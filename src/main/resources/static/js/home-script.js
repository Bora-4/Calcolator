class NavigationManager {
    constructor() {

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
                    event.preventDefault();
                    alert("Please enter a valid email address.");
                    return;
                }
                if (!password) {
                    event.preventDefault();
                    alert("Password is required.");
                }
            });
        }

        const signUpForm = document.querySelector(".signup form");
        if (signUpForm) {
            signUpForm.addEventListener("submit", (event) => {
                event.preventDefault();

                const username = signUpForm.querySelector("input[name='name']").value.trim();
                const email = signUpForm.querySelector("input[name='email']").value.trim();
                const password = signUpForm.querySelector("input[name='password']").value.trim();


                if (!username) {
                    alert("Username is required.");
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


                const userDTO = {
                    name: username,
                    email: email,
                    role: "user",
                    password: password
                };


                fetch("/users/signup", {
                    method: "POST",
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(userDTO),
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
                    .then(() => {
                        alert("Account created successfully!");
                        window.location.href = "/login";
                    })
                    .catch((error) => {
                        alert(error.message); e
                        console.error("Error:", error);
                    });
            });
        }
    }

    validateEmail(email) {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const navManager = new NavigationManager();
    navManager.attachNavigationEvents();
    navManager.attachFormValidation();
});