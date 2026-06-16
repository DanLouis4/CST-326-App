document.addEventListener("DOMContentLoaded", function () {

    const favoriteForms = document.querySelectorAll(".favorite-form");

    favoriteForms.forEach(function (form) {

        form.addEventListener("submit", async function (event) {
            event.preventDefault();

            const button = form.querySelector(".favorite-btn");
            const icon = button.querySelector("i");

            button.disabled = true;

            try {
                const response = await fetch(form.action, {
                    method: "POST",
                    body: new FormData(form),
                    headers: {
                        "X-Requested-With": "XMLHttpRequest",
                        "Accept": "application/json"
                    }
                });

                if (!response.ok) {
                    throw new Error("Favorite request failed.");
                }

                const result = await response.json();

                button.classList.toggle("is-favorite", result.favorited);
                button.setAttribute("aria-pressed", result.favorited);

                icon.classList.toggle("bi-heart-fill", result.favorited);
                icon.classList.toggle("bi-heart", !result.favorited);

            } catch (error) {
                console.error(error);
            } finally {
                button.disabled = false;
            }
        });
    });
});