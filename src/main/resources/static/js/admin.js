
    const foodEntries = [];

    function renderFoodTable() {
    const tableBody = document.getElementById('foodTableBody');
    tableBody.innerHTML = '';

    foodEntries.forEach((entry, index) => {
    const row = document.createElement('tr');
    row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${entry.name}</td>
                    <td>${entry.calories}</td>
                    <td>${entry.price}</td>
                    <td>
                        <button class="btn" onclick="editFoodEntry(${index})">Edit</button>
                        <button class="btn" onclick="deleteFoodEntry(${index})">Delete</button>
                    </td>
                `;
    tableBody.appendChild(row);
});
}

    function addNewFoodEntry() {
    const name = prompt('Enter food name:');
    const calories = parseInt(prompt('Enter calories:'), 10);
    const price = parseFloat(prompt('Enter price:'));

    if (name && !isNaN(calories) && !isNaN(price)) {
    foodEntries.push({ name, calories, price });
    renderFoodTable();
} else {
    alert('Invalid input!');
}
}

    function editFoodEntry(index) {
    const entry = foodEntries[index];
    const name = prompt('Enter food name:', entry.name);
    const calories = parseInt(prompt('Enter calories:', entry.calories), 10);
    const price = parseFloat(prompt('Enter price:', entry.price));

    if (name && !isNaN(calories) && !isNaN(price)) {
    foodEntries[index] = { name, calories, price };
    renderFoodTable();
} else {
    alert('Invalid input!');
}
}

    function deleteFoodEntry(index) {
    if (confirm('Are you sure you want to delete this entry?')) {
    foodEntries.splice(index, 1);
    renderFoodTable();
}
}

    // Example Report Data
    document.getElementById('last7Days').textContent = '15';
    document.getElementById('avgCalories').textContent = '2200';
    document.getElementById('exceedingUsers').innerHTML = '<li>John Doe</li><li>Jane Smith</li>';

