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
            <td>${entry.createdAt}</td>
            <td>${entry.updatedAt}</td>
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
        const endDate = document.getElementById("endDate").value;

        if (!startDate || !endDate) {
            alert("Both start date and end date are required.");
            return;
        }
        if (startDate > endDate) {
            alert("Start date cannot be later than end date.");
            return;
        }

        fetchEntries(startDate, endDate);
    });
});

function openModal() {
    document.getElementById('foodEntryModal').style.display = "block";
}

function closeModal() {
    document.getElementById('foodEntryModal').style.display = "none";
}

document.getElementById('viewWeeklySummaryButton').addEventListener('click', function() {
    let weeklyData = [
        { day: 'Monday', calories: 2500, thresholdExceeded: true },
        { day: 'Tuesday', calories: 2000, thresholdExceeded: false },
        { day: 'Wednesday', calories: 3000, thresholdExceeded: true },
        { day: 'Thursday', calories: 2200, thresholdExceeded: false },
        { day: 'Friday', calories: 2700, thresholdExceeded: true },
        { day: 'Saturday', calories: 1900, thresholdExceeded: false },
        { day: 'Sunday', calories: 3100, thresholdExceeded: true },
    ];

    let tableBody = document.getElementById('weeklySummaryTableBody');
    let totalCalories = 0;
    let totalExpenditure = 0;
    let daysExceeded = 0;

    tableBody.innerHTML = '';
    weeklyData.forEach((data) => {
        let row = document.createElement('tr');

        let dayCell = document.createElement('td');
        dayCell.textContent = data.day;
        row.appendChild(dayCell);

        let caloriesCell = document.createElement('td');
        caloriesCell.textContent = data.calories;
        row.appendChild(caloriesCell);

        let exceededCell = document.createElement('td');
        exceededCell.textContent = data.thresholdExceeded ? 'Yes' : 'No';
        row.appendChild(exceededCell);

        tableBody.appendChild(row);

        totalCalories += data.calories;
        if (data.thresholdExceeded) daysExceeded++;
        totalExpenditure += (data.calories * 0.05);
    });

    document.getElementById('totalCaloriesWeek').textContent = totalCalories;
    document.getElementById('thresholdExceededDays').textContent = daysExceeded;
    document.getElementById('totalExpenditureWeek').textContent = totalExpenditure.toFixed(2);

    document.getElementById('weeklySummaryModal').style.display = 'block';
});

document.getElementById('closeWeeklySummaryModal').addEventListener('click', function() {
    document.getElementById('weeklySummaryModal').style.display = 'none';
});
