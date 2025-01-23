const searchInput = document.querySelector('.search-user input');
const userList = document.querySelector('.user-list ul');
const foodEntries = [];
let selectedUserId = null;
let allUsers = [];

// Fetch all users and populate the user list
document.addEventListener("DOMContentLoaded", () => {
    fetchUsers();
});

// Fetch users (either all or filtered by search term)
async function fetchUsers(searchTerm = '') {
    try {
        const url = searchTerm ? `/users?search=${searchTerm}` : '/users';
        const response = await fetch(url);
        const users = await response.json();

        allUsers = users;
        renderUserList(users);

    } catch (error) {
        console.error('Error fetching users:', error);
    }
}


function renderUserList(users) {
    userList.innerHTML = '';

    users.forEach(user => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.email}</td>
            <td><button class="choose-btn" data-user-id="${user.id}">Choose</button></td>
        `;
        const chooseButton = listItem.querySelector('.choose-btn');
        chooseButton.addEventListener('click', () => {
            selectedUserId = user.id;
            console.log('User selected:', selectedUserId);
            fetchUserFoodEntries(selectedUserId);
        });
        userList.appendChild(listItem);
    });
}


// Handle the search input to filter users
searchInput.addEventListener('input', () => {
    const searchTerm = searchInput.value.trim();
    console.log("Searching for:", searchTerm);
    if (searchTerm === '') {
        renderUserList(allUsers);
    } else {
        const filteredUsers = allUsers.filter(user => user.name.toLowerCase().includes(searchTerm.toLowerCase()));
        console.log("Filtered users:", filteredUsers);
        renderUserList(filteredUsers);
    }
});


// Fetch food entries for the selected user for the current day
async function fetchUserFoodEntries(userId) {

    console.log("Fetching food entries for user:", userId);

    try {


        const response = await fetch(`/food-entries`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            },

        });

        if (!response.ok) {
            throw new Error("Failed to fetch food entries");
        }

        // Parse the response data
        const entries = await response.json();
        console.log("Food entries:", entries);

        if (entries && entries.length > 0) {
            foodEntries.length = 0;
            foodEntries.push(...entries);
            renderFoodTable();
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
    tableBody.innerHTML = '';

    console.log("Rendering table with food entries:", foodEntries);
    console.log("Selected user ID:", selectedUserId);

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
                    'Accept': 'application/json'
                },
                body: JSON.stringify({
                    foodName,
                    calories,
                    price
                }),
                credentials: 'include',
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
    const entryDate = entry.entryDate ? entry.entryDate : new Date().toISOString();

    const userId = entry.user ? entry.user.id : null;

    if (foodName && !isNaN(calories) && !isNaN(price)) {
        try {
            const response = await fetch(`/food-entries/${entry.id}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    id: entry.id,
                    foodName: foodName,
                    calories: calories,
                    price: price,
                    entryDate: entryDate,
                    user: {id: userId}
                }),
            });


            const responseData = await response.json();
            console.log('Response:', responseData);

            if (response.ok) {
                foodEntries[index] = {id: entry.id, foodName, calories, price, entryDate, user: {id: userId}};
                console.log('Updated foodEntries:', foodEntries);
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
        .then(response => response.json())
        .then(data => {
            if (data.message === "Report created successfully.") {
                alert(data.message);
                if (!data.reportId) {
                    console.error('Report ID is missing in response:', data);
                    alert('An error occurred: Report ID is missing.');
                }
                const reportId = data.reportId;


                fetchWeeklyStatistics(reportId);
            } else {
                alert("Error: " + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('An error occurred while generating the report.');
        });
}

async function fetchWeeklyStatistics(reportId) {
    try {
        const response = await fetch(`/reports/${reportId}/weekly-statistics`);
        const statistics = await response.json();

        if (statistics && statistics.length > 0) {
            renderWeeklyStatistics(statistics);
        } else {
            alert('No weekly statistics found.');
        }
    } catch (error) {
        console.error('Error fetching weekly statistics:', error);
        alert('Failed to fetch weekly statistics.');
    }
}

function renderWeeklyStatistics(statistics) {
    const statisticsList = document.getElementById('weeklyStatisticsList');
    statisticsList.innerHTML = ''; // Clear the existing list

    statistics.forEach(stat => {
        const listItem = document.createElement('li');
        listItem.innerHTML = `
            <p><strong>Statistic Name:</strong> ${stat.statisticName}</p>
            <p><strong>Statistic Value:</strong> ${stat.statisticValue}</p>
        `;
        statisticsList.appendChild(listItem);
    });
}

// Fetch and display users exceeding their monthly limit
function fetchUsersExceedingLimit() {
    fetch('/expenditure/exceeded-limit')
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Failed to fetch users exceeding the limit.');
            }
        })
        .then(data => {
            const tableBody = document.getElementById('exceededLimitTableBody');
            tableBody.innerHTML = ''; // Clear existing rows

            // Populate table with data
            Object.entries(data).forEach(([userId, exceededAmount]) => {
                const row = document.createElement('tr');

                const userIdCell = document.createElement('td');
                userIdCell.textContent = userId;

                const exceededAmountCell = document.createElement('td');
                exceededAmountCell.textContent = exceededAmount.toFixed(2); // Format to two decimal places

                row.appendChild(userIdCell);
                row.appendChild(exceededAmountCell);

                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error fetching users exceeding monthly limit. Please try again.');
        });
}

// Automatically fetch users exceeding the limit when the page loads
document.addEventListener('DOMContentLoaded', fetchUsersExceedingLimit);









