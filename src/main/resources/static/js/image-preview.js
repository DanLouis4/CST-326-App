function loadImagePreview(inputId, previewId, placeholderId)
{
    const imageUrlInput = document.getElementById(inputId);
    const imagePreview = document.getElementById(previewId);
    const placeholder = document.getElementById(placeholderId);

    if (!imagePreview || !placeholder)
    {
        return;
    }

    let imageUrl = "";

    if (imageUrlInput)
    {
        imageUrl = imageUrlInput.value;
    }
    else
    {
        imageUrl = imagePreview.getAttribute("data-image-url");
    }

    if (!imageUrl)
    {
        imagePreview.style.display = "none";
        placeholder.style.display = "flex";
        imagePreview.removeAttribute("src");
        return;
    }

    imagePreview.onload = function ()
    {
        imagePreview.style.display = "block";
        placeholder.style.display = "none";
    };

    imagePreview.onerror = function ()
    {
        imagePreview.style.display = "none";
        placeholder.style.display = "flex";
        imagePreview.removeAttribute("src");
    };

    imagePreview.src = imageUrl;
}