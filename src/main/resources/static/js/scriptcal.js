class NavigationManager {
    constructor() {
        // Define available pages
        this.pages = {
            home: "/home",
            login: "/login",
            signup: "/signup",
        };
    }


    navigateTo(page) {
        if (this.pages[page]) {
            window.location.href = this.pages[page];
        } else {
            console.error(`Page "${page}" not found!`);
        }
    }


    attachNavigationEvents() {
        document.querySelectorAll("[data-navigate]").forEach((button) => {
            button.addEventListener("click", (event) => {
                const page = event.target.getAttribute("data-navigate");
                this.navigateTo(page);
            });
        });
    }


    attachFormValidation() {
        const logInForm = document.querySelector(".login form");
        if (logInForm) {
            logInForm.addEventListener("submit", (event) => {
                const email = logInForm.querySelector("input[type='email']").value.trim();
                const password = logInForm.querySelector("input[type='password']").value.trim();

                if (!this.validateEmail(email)) {
                    event.preventDefault(); // Stop form submission only if validation fails
                    alert("Please enter a valid email address.");
                    return;
                }
                if (!password) {
                    event.preventDefault(); // Stop form submission only if validation fails
                    alert("Password is required.");

                }
            });

        }


        const signUpForm = document.querySelector(".signup form");
        if (signUpForm) {
            signUpForm.addEventListener("submit", (event) => {
                const username = signUpForm.querySelector("input[name='username']").value.trim();
                const email = signUpForm.querySelector("input[name='email']").value.trim();
                const password = signUpForm.querySelector("input[name='password']").value.trim();

                if (!username) {
                    event.preventDefault(); // Prevent submission if validation fails
                    alert("Username is required.");
                    return;
                }
                if (!this.validateEmail(email)) {
                    event.preventDefault(); // Prevent submission if validation fails
                    alert("Please enter a valid email address.");
                    return;
                }
                if (!password) {
                    event.preventDefault(); // Prevent submission if validation fails
                    alert("Password is required.");

                }
                // Allow submission if validation passes
            });
        }
    }


    validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
}

// Initialize the NavigationManager
document.addEventListener("DOMContentLoaded", () => {
    const navManager = new NavigationManager();
    navManager.attachNavigationEvents();
    navManager.attachFormValidation();
});
