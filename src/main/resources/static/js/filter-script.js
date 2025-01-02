document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("filterForm");
    const tableBody = document.getElementById("foodEntriesTableBody");

    // Function to show a message in the table when no results are found
    const showMessage = (message) => {
        tableBody.innerHTML = `
            <tr>
                <td colspan="6" style="text-align: center; font-style: italic;">${message}</td>
            </tr>`;
    };

    // Function to populate the table with data
    const populateTable = (data) => {
        if (data.length === 0) {
            showMessage("No entries found for the selected date range.");
            return;
        }
        tableBody.innerHTML = ""; // Clear previous entries
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
    };

    // Function to fetch entries
    const fetchEntries = (startDate, endDate) => {
        showMessage("Loading...");
        fetch(`/food-entries/filter-by-date?startDate=${startDate}&endDate=${endDate}`)
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
    };

    // Get today's date in YYYY-MM-DD format
    const today = new Date().toISOString().split("T")[0];
    const startOfDay = `${today}T00:00:00`;
    const endOfDay = `${today}T23:59:59`;

    // Fetch and display entries for today on load
    fetchEntries(startOfDay, endOfDay);

    // Handle form submissions for date filtering
    form.addEventListener("submit", (event) => {
        event.preventDefault();
        const startDate = document.getElementById("startDate").value;
        const endDate = document.getElementById("endDate").value;

        // Basic form validation
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