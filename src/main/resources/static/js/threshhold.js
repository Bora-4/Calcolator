const calorieSlider = document.getElementById('calorieSlider');
const calorieValue = document.getElementById('calorieValue');
const calorieWarning = document.getElementById('calorieWarning');
const totalCaloriesSpan = document.getElementById('totalCalories');
const closeWarningBtn = document.getElementById('closeWarningBtn');
const doneSection = document.getElementById('doneSection');
const doneBtn = document.getElementById('doneBtn');


let totalCalories = 2500;


calorieSlider.addEventListener('input', () => {
    totalCalories = parseInt(calorieSlider.value, 10);
    calorieValue.textContent = totalCalories;

    if (totalCalories > 2500) {
        // Show warning if exceeded
        calorieWarning.classList.remove('hidden');
        doneSection.classList.add('hidden');
        totalCaloriesSpan.textContent = totalCalories;
    } else if (totalCalories >= 1000 && totalCalories <= 2500) {
        // Show Done button if within valid range
        calorieWarning.classList.add('hidden');
        doneSection.classList.remove('hidden');
    } else {

        calorieWarning.classList.add('hidden');
        doneSection.classList.add('hidden');
    }
});

closeWarningBtn.addEventListener('click', () => {
    calorieWarning.classList.add('hidden');
});

doneBtn.addEventListener('click', () => {
    alert(`Threshold of ${totalCalories} kcal set successfully!`);
});
