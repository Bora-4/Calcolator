const searchInput = document.querySelector('.search-user input');
const userList = document.querySelector('.user-list ul');
const foodEntries = [];
let selectedUserId = null;
let allUsers = [];

// Fetch all users and populate the user list
document.addEventListener("DOMContentLoaded", () => {
    fetchUsers(); // Fetch all users on page load
});

// Fetch users (either all or filtered by search term)
async function fetchUsers(searchTerm = '') {
    try {
        const url = searchTerm ? `/users?search=${searchTerm}` : '/users'; // Modify this if you have a search endpoint
        const response = await fetch(url);
        const users = await response.json();

        allUsers = users; // Save all users for later filtering
        renderUserList(users); // Render the user list

    } catch (error) {
        console.error('Error fetching users:', error);
    }
}

// Render the user list in the UI
function renderUserList(users) {
    userList.innerHTML = ''; // Clear the existing user list

    users.forEach(user => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td><button class="choose-btn" data-user-id="${user.id}">Choose</button></td>
        `; // Display user name and email
        const chooseButton = listItem.querySelector('.choose-btn');
        chooseButton.addEventListener('click', () => {
            selectedUserId = user.id;
            console.log('User selected:', selectedUserId);
            fetchUserFoodEntries(selectedUserId); // Fetch food entries for the selected user
        });
        userList.appendChild(listItem);
    });
}


// Handle the search input to filter users
searchInput.addEventListener('input', () => {
    const searchTerm = searchInput.value.trim();  // Get the search term
    console.log("Searching for:", searchTerm); // Log search term for debugging
    if (searchTerm === '') {
        renderUserList(allUsers); // If search is empty, render all users
    } else {
        const filteredUsers = allUsers.filter(user => user.name.toLowerCase().includes(searchTerm.toLowerCase()));
        console.log("Filtered users:", filteredUsers); // Log filtered users for debugging
        renderUserList(filteredUsers); // Render the filtered list
    }
});


// Fetch food entries for the selected user for the current day
async function fetchUserFoodEntries(userId) {

    console.log("Fetching food entries for user:", userId); // Log user ID for debugging

    try {

        // Send request to backend to fetch food entries for today
        const response = await fetch(`/food-entries`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },
            //credentials: 'include' // Make sure the session is maintained (logged in user)
        });

        if (!response.ok) {
            throw new Error("Failed to fetch food entries");
        }

        // Parse the response data
        const entries = await response.json();
        console.log("Food entries:", entries); // Log entries for debugging

        if (entries && entries.length > 0) {
            foodEntries.length = 0; // Clear the existing entries
            foodEntries.push(...entries); // Add new entries
            renderFoodTable(); // Render the food entries table
        } else {
            alert('No food entries found for today.');
        }
    } catch (error) {
        console.error('Error fetching food entries:', error);
    }
}

// Render the food entries table
function renderFoodTable() {
    const tableBody = document.getElementById('foodTableBody');
    tableBody.innerHTML = ''; // Clear the existing table body

    console.log("Rendering table with food entries:", foodEntries);  // Debug log
    console.log("Selected user ID:", selectedUserId);  // Debug log

    if (foodEntries.length === 0) {
        const row = document.createElement('tr');
        row.innerHTML = `<td colspan="5">No food entries available</td>`;
        tableBody.appendChild(row);
        return;
    }

    foodEntries.forEach((entry, index) => {
        const row = document.createElement('tr');
        if (entry.user.id === selectedUserId) {
            row.innerHTML = `
            <td>${index + 1}</td>
            <td>${entry.foodName}</td>
            <td>${entry.calories}</td>
            <td>${entry.price}</td>
            <td>
                <button class="btn" onclick="editFoodEntry(${index})">Edit</button>
                <button class="btn" onclick="deleteFoodEntry(${index})">Delete</button>
            </td>
        `;
        }


        tableBody.appendChild(row);
    });
}


// Other functions like addNewFoodEntry, editFoodEntry, deleteFoodEntry, and logout remain the same


async function addNewFoodEntry() {
    if (!selectedUserId) {
        alert('Please select a user first!');
        return;
    }

    const foodName = prompt('Enter food name:');
    const calories = parseInt(prompt('Enter calories:'), 10);
    const price = parseFloat(prompt('Enter price:'));

    if (foodName && !isNaN(calories) && !isNaN(price)) {
        try {
            const response = await fetch(`/food-entries/save?userId=${selectedUserId}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Accept': 'application/json' // Optional, but helpful for JSON responses
                },
                body: JSON.stringify({
                    foodName,
                    calories,
                    price
                }),
                credentials: 'include', // Ensures cookies are sent with the request
            });

            if (response.ok) {
                const newEntry = await response.json();
                foodEntries.push(newEntry);
                renderFoodTable();
                alert('Food entry added successfully!');
            } else {
                const errorResponse = await response.json();
                alert(`Error: ${errorResponse.message}`);
            }
        } catch (error) {
            console.error('Error adding food entry:', error);
            alert(error);
        }
    } else {
        alert('Invalid input!');
    }
}


async function editFoodEntry(index) {
    const entry = foodEntries[index];
    const foodName = prompt('Enter food name:', entry.name);
    const calories = parseInt(prompt('Enter calories:', entry.calories), 10);
    const price = parseFloat(prompt('Enter price:', entry.price));
    const entryDate = entry.entryDate ? entry.entryDate : new Date().toISOString();  // Use the existing date or current date as fallback

    const userId = entry.user ? entry.user.id : null;

    if (foodName && !isNaN(calories) && !isNaN(price)) {
        try {
            const response = await fetch(`/food-entries/${entry.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: entry.id, // Ensure the ID is passed correctly
                    foodName: foodName,
                    calories: calories,
                    price: price,
                    entryDate: entryDate,
                    user: {id: userId}
                }),
            });

            // Check if the response is ok and log it
            const responseData = await response.json();  // Assuming the API sends a JSON response
            console.log('Response:', responseData);

            if (response.ok) {
                foodEntries[index] = {id: entry.id, foodName, calories, price, entryDate, user: {id: userId}};
                console.log('Updated foodEntries:', foodEntries);  // Log the updated foodEntries array
                renderFoodTable();
                alert('Food entry updated successfully!');
            } else {
                const errorData = await response.json();
                alert(`Error: ${errorData.message}`);
            }
        } catch (error) {
            console.error('Error updating food entry:', error);
        }
    } else {
        alert('Invalid input!');
    }
}

// Delete a food entry
async function deleteFoodEntry(index) {
    const entry = foodEntries[index];
    if (confirm('Are you sure you want to delete this entry?')) {
        try {
            const response = await fetch(`/food-entries/${entry.id}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                foodEntries.splice(index, 1);
                renderFoodTable();
            } else {
                alert('Error deleting food entry.');
            }
        } catch (error) {
            console.error('Error deleting food entry:', error);
        }
    }
}

// Logout functionality
const logoutBtn = document.getElementById('logout-btn');
logoutBtn.addEventListener('click', async () => {
    try {
        await fetch('/logout', {method: 'GET'});
        window.location.href = '/home';
    } catch (error) {
        console.error('Error during logout:', error);
    }
});

function generateReport() {
    event.preventDefault();

    fetch('/reports', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        }
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                return response.json().then(errorData => {
                    throw new Error(errorData.message || "An error occurred.");
                });
            }
        })
        .then(data => {
            alert(data.message);
        })
        .catch(error => {
            console.error('Error:', error);
            alert(error.message);
        });
}


