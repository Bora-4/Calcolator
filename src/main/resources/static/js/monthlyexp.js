let totalExpenditure = 1200;
let threshold = 200;

function updateThreshold() {
    const thresholdInput = document.getElementById('expenditureThreshold');
    threshold = parseFloat(thresholdInput.value);
    alert('Your monthly expenditure threshold has been updated to €' + threshold);
}

function checkExpenditure() {
    const totalExpenditureElement = document.getElementById('totalExpenditure');
    totalExpenditureElement.textContent = totalExpenditure;

    const warningElement = document.getElementById('warning');
    const overAmountElement = document.getElementById('overAmount');

    if (totalExpenditure > threshold) {
        const overAmount = totalExpenditure - threshold;
        overAmountElement.textContent = overAmount;
        warningElement.classList.remove('hidden');
    } else {
        warningElement.classList.add('hidden');
    }
}
