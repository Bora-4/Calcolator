function loadUserData() {
    const userData = {
        name: "User",
        foodCount: 3,
        totalCalories: 1500,
        totalMoney: 500,
    };

    document.getElementById('name').value = userData.name;
    document.getElementById('food-count').textContent = userData.foodCount;
    document.getElementById('calories').textContent = userData.totalCalories;
    document.getElementById('money').textContent = userData.totalMoney.toFixed(2);
}

function navigateToAddFoodEntry() {
    window.location.href = "/add-food-entry";
}

function logout() {
    fetch('/logout', {
        method: 'GET'
    })
        .then(() => {
            window.location.href = "/home";
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function showMessage(message) {
    const tableBody = document.getElementById("foodEntriesTableBody");
    tableBody.innerHTML = `
        <tr>
            <td colspan="6" style="text-align: center; font-style: italic;">${message}</td>
        </tr>`;
}

function populateTable(data) {
    const tableBody = document.getElementById("foodEntriesTableBody");
    if (data.length === 0) {
        showMessage("No entries found for the selected date range.");
        return;
    }
    tableBody.innerHTML = "";
    data.forEach(entry => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${entry.foodName}</td>
            <td>${entry.calories}</td>
            <td>${entry.price}</td>
            <td>${entry.entryDate}</td>
        `;
        tableBody.appendChild(row);
    });
}

function fetchEntries(startDate, endDate) {
    showMessage("Loading...");
    fetch(`food-entries/filter-by-date?startDate=${startDate}&endDate=${endDate}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch data.");
            }
            return response.json();
        })
        .then(data => populateTable(data))
        .catch(error => {
            console.error('Error:', error);
            showMessage("An error occurred while fetching data. Please try again later.");
        });
}


document.getElementById('foodEntryForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const foodName = document.getElementById('foodName').value;
    const calories = document.getElementById('calories').value;
    const price = document.getElementById('price').value;

    const foodEntry = {
        foodName: foodName,
        calories: calories,
        price: price
    };

    fetch('/food-entries/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(foodEntry)
    })
        .then((response) => {
            if (response.ok) {
                return response.json();
            } else {

                return response.json().then((errorData) => {
                    throw new Error(errorData.message || "An error occurred.");
                });
            }
        })
        .then(data => {
            alert(data.message);
        })
        .catch(error => {
            console.error('Error:',error);
            alert(error.message);
        });
});


document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("filterForm");
    const today = new Date().toISOString().split("T")[0];
    const startOfDay = `${today}T00:00:00`;
    const endOfDay = `${today}T23:59:59`;
    fetchEntries(startOfDay, endOfDay);

    form.addEventListener("submit", (event) => {
        event.preventDefault();
        const startDate = document.getElementById("startDate").value;
        const startTime = document.getElementById("startTime").value;
        const endDate = document.getElementById("endDate").value;
        const endTime = document.getElementById("endTime").value;

        if (!startDate || !endDate) {
            alert("Both start date and end date are required.");
            return;
        }
        if (startDate > endDate) {
            alert("Start date cannot be later than end date.");
            return;
        }
        let start = new Date(startDate + 'T' + startTime).toISOString();
        let end = new Date(endDate + 'T' + endTime).toISOString();

        // Remove the 'Z' from the datetime strings before sending
        start = start.replace("Z", "");
        end = end.replace("Z", "");

        fetchEntries(start, end);
    });
});

function openModal() {
    document.getElementById('foodEntryModal').style.display = "block";
}

function closeModal() {
    document.getElementById('foodEntryModal').style.display = "none";
}

document.getElementById('viewWeeklySummaryButton').addEventListener('click', function() {
    last7DaysEntries();
});

document.getElementById('closeWeeklySummaryModal').addEventListener('click', function() {
    document.getElementById('weeklySummaryModal').style.display = 'none';
});
function fetchTodayEntries() {
    const today = new Date().toISOString().split("T")[0];
    const startOfDay = `${today}T00:00:00`;
    const endOfDay = `${today}T23:59:59`;
    showMessage("Loading...");
    fetch(`food-entries/filter-by-date?startDate=${startOfDay}&endDate=${endOfDay}`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch data: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            // Normalize response format if needed
            const normalizedData = Array.isArray(data) ? { entries: data } : data;
            updateOverview(normalizedData);
        })
        .catch(error => {
            console.error('Error:', error.message);
            showMessage("An error occurred while fetching data. Please try again later.");
        });

}

function updateOverview(data) {
    const entries = Array.isArray(data) ? data : data.entries;

    if (!Array.isArray(entries)) {
        console.warn("Invalid data format:", data);
        return;
    }

    const foodCount = entries.length;
    const totalCalories = entries.reduce((total, entry) => total + (entry.calories || 0), 0);
    const totalExpenditure = entries.reduce((total, entry) => total + (entry.price || 0), 0);

    document.getElementById("food-count").textContent = foodCount;
    document.getElementById("total_calories").textContent = totalCalories;
    document.getElementById("money").textContent = totalExpenditure.toFixed(2);
}


document.addEventListener("DOMContentLoaded", () => {
    fetchTodayEntries();
});

function last7DaysEntries() {
    showMessage("Loading...");

    fetch(`food-entries/last-7-days`)
        .then(response => {
            if (!response.ok) {
                throw new Error(`Failed to fetch data: ${response.statusText}`);
            }
            return response.json();
        })
        .then(data => {
            const normalizedData = Array.isArray(data) ? { entries: data } : data;
            viewWeeklySummary(normalizedData);
        })
        .catch(error => {
            console.error('Error:', error.message);
            showMessage("An error occurred while fetching data. Please try again later.");
        });
}

function viewWeeklySummary(normalizedData) {
    const entries = Array.isArray(normalizedData.entries) ? normalizedData.entries : [];

    if (!entries.length) {
        console.warn("No valid entries found:", normalizedData);
        return;
    }

    // Aggregate calories and calculate total expenditure for the week
    const dayMap = entries.reduce((map, entry) => {
        const day = new Date(entry.entryDate).toLocaleDateString('en-US', { weekday: 'long' }); // Get day name
        if (!map[day]) {
            map[day] = { calories: 0, entries: [] };
        }
        map[day].calories += entry.calories || 0;
        map[day].entries.push(entry);
        return map;
    }, {});

    const aggregatedDays = Object.keys(dayMap).map(day => ({
        day,
        calories: dayMap[day].calories,
        thresholdExceeded: dayMap[day].calories > 2500,
    }));

    // Calculate total expenditure
    const totalExpenditure = entries.reduce((sum, entry) => sum + (entry.price || 0), 0);

    // Render the table
    let tableBody = document.getElementById('weeklySummaryTableBody');
    tableBody.innerHTML = '';

    let totalCalories = 0;
    let daysExceeded = 0;

    aggregatedDays.forEach((entry) => {
        let row = document.createElement('tr');

        let dayCell = document.createElement('td');
        dayCell.textContent = entry.day || "Unknown";
        row.appendChild(dayCell);

        let caloriesCell = document.createElement('td');
        caloriesCell.textContent = entry.calories || 0;
        row.appendChild(caloriesCell);

        let exceededCell = document.createElement('td');
        exceededCell.textContent = entry.thresholdExceeded ? 'Yes' : 'No';
        row.appendChild(exceededCell);

        tableBody.appendChild(row);

        totalCalories += entry.calories || 0;
        if (entry.thresholdExceeded) daysExceeded++;
    });

    // Update summary elements
    document.getElementById('totalCaloriesWeek').textContent = totalCalories;
    document.getElementById('thresholdExceededDays').textContent = daysExceeded;
    document.getElementById('totalExpenditureWeek').textContent = totalExpenditure.toFixed(2) + ' €';

    document.getElementById('weeklySummaryModal').style.display = 'block';
}


