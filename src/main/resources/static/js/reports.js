const weeklySummary = {
    totalCalories: 15000,
    thresholdBreaches: 3,
    totalExpenditure: 125.50,
    dailyData: [
        { date: "2025-01-14", calories: 2400, expenditure: 20.5 },
        { date: "2025-01-15", calories: 2600, expenditure: 18.75 },
        { date: "2025-01-16", calories: 3100, expenditure: 25.0 },
        { date: "2025-01-17", calories: 2800, expenditure: 15.25 },
        { date: "2025-01-18", calories: 2200, expenditure: 17.0 },
        { date: "2025-01-19", calories: 3000, expenditure: 14.0 },
        { date: "2025-01-20", calories: 2700, expenditure: 15.0 }
    ]
};
const tableBody = document.querySelector("#reportTable tbody");
weeklySummary.dailyData.forEach(day => {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${day.date}</td>
        <td>${day.calories}</td>
        <td>${day.expenditure.toFixed(2)}</td>
    `;
    tableBody.appendChild(row);
});


const dates = weeklySummary.dailyData.map(day => day.date);
const calories = weeklySummary.dailyData.map(day => day.calories);
const expenditures = weeklySummary.dailyData.map(day => day.expenditure);

const caloriesChart = new Chart(document.getElementById("caloriesChart"), {
    type: "bar",
    data: {
        labels: dates,
        datasets: [{
            label: "Calories Consumed",
            data: calories,
            backgroundColor: "rgba(75, 192, 192, 0.6)",
            borderColor: "rgba(75, 192, 192, 1)",
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        plugins: {
            title: {
                display: true,
                text: "Calories Consumed Per Day"
            }
        }
    }
});
t
const expenditureChart = new Chart(document.getElementById("expenditureChart"), {
    type: "line",
    data: {
        labels: dates,
        datasets: [{
            label: "Expenditure ($)",
            data: expenditures,
            borderColor: "rgba(153, 102, 255, 1)",
            backgroundColor: "rgba(153, 102, 255, 0.2)",
            fill: true,
            tension: 0.4
        }]
    },
    options: {
        responsive: true,
        plugins: {
            title: {
                display: true,
                text: "Daily Expenditure ($)"
            }
        }
    }
});
