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
                event.preventDefault();
                const email = logInForm.querySelector("input[type='email']").value.trim();
                const password = logInForm.querySelector("input[type='password']").value.trim();

                if (!this.validateEmail(email)) {
                    alert("Please enter a valid email address.");
                    return;
                }
                if (!password) {
                    alert("Password is required.");
                    return;
                }

                alert("Sign In successful!");

            });
        }


        const signUpForm = document.querySelector(".signup form");
        if (signUpForm) {
            signUpForm.addEventListener("submit", (event) => {
                event.preventDefault();
                const firstName = signUpForm.querySelector("input[placeholder='First Name']").value.trim();
                const lastName = signUpForm.querySelector("input[placeholder='Last Name']").value.trim();
                const email = signUpForm.querySelector("input[type='email']").value.trim();
                const password = signUpForm.querySelector("input[type='password']").value.trim();

                if (!firstName) {
                    alert("First Name is required.");
                    return;
                }
                if (!lastName) {
                    alert("Last Name is required.");
                    return;
                }
                if (!this.validateEmail(email)) {
                    alert("Please enter a valid email address.");
                    return;
                }
                if (!password) {
                    alert("Password is required.");
                    return;
                }

                alert("Sign Up successful!");

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
