/* eslint-env browser */
import { TectonicKlaviyo } from '@tectonic-technologies/klaviyo-capacitor-plugin';

window.initializeKlaviyo = async () => {
    const apiKey = document.getElementById("apiKeyInput").value;
    
    if (!apiKey) {
        alert("Please enter an API key");
        return;
    }
    
    try {
        await TectonicKlaviyo.initialize({ apiKey });
        alert("Klaviyo SDK initialized successfully!");
    } catch (error) {
        alert(`Failed to initialize: ${error.message}`);
    }
}

window.testAddedToCartEvent = async () => {
    try {
        const eventPayload = {
            name: "Added to Cart",
            value: 52.99,
            properties: {
                "VariantID": "49392897065153",
                "Product Title": "Axe & Sledge Farm Fed Protein",
                "ItemNames": [
                    "Strawberry Milkshake"
                ],
                "Product ID": "1810271109163",
                "Product Badge Titles List": [],
                "AddedItemProductName": "Axe & Sledge Farm Fed Protein",
                "VariantTitle": "Strawberry Milkshake",
                "AddedItemCategories": [],
                "Title": "Axe & Sledge Farm Fed Protein",
                "Variant Title": "Strawberry Milkshake",
                "Variant Price": 52.99,
                "Variant Id": "49392897065153",
                "Product Tags": [],
                "Items": [
                    {
                        "Quantity": 1,
                        "ImageURL": "https://cdn.shopify.com/s/files/1/2640/1510/files/StrawMilkfarm.webp?v=1753215138",
                        "ProductName": "Axe & Sledge Farm Fed Protein",
                        "ProductURL": "http://nutrition-faktory.myshopify.com/products/farm-fed-30srv?variant=49392897065153",
                        "ProductID": "1810271109163",
                        "ItemPrice": 52.99,
                        "RowTotal": 52.99,
                        "SKU": "810164581259",
                        "ProductCategories": []
                    }
                ],
                "Compare At Price": 52.99,
                "Currency Code": "USD",
                "CheckoutURL": "https://shop.nutritionfaktory.com/cart/c/hWN36dLzKsSL2gkSGN7ohkq9?key=IljG0lIZF58nx6aEmQSJknUEgL35XuB-wbqYSszyOh8-Ckx2G8txvU4G_bWHUeKuXXKt9WfeSwzuWa-gRb__BxPD1fjZa_-v-nSsqH-8sIrC6NcwNosOv0esaIjkgtdKEHN-Zw7Fp39SPk6OdUSVFg%3D%3D",
                "Price": 52.99,
                "Product Id": "1810271109163",
                "AddedItemPrice": 52.99,
                "ProductPrice": 52.99,
                "AddedItemURL": "http://nutrition-faktory.myshopify.com/products/farm-fed-30srv?variant=49392897065153",
                "Image Url": "https://cdn.shopify.com/s/files/1/2640/1510/files/StrawMilkfarm.webp?v=1753215138",
                "VariantPrice": 52.99,
                "AddedItemProductID": "1810271109163",
                "Product Handle": "farm-fed-30srv",
                "AddedItemQuantity": 1,
                "Url": "http://nutrition-faktory.myshopify.com/products/farm-fed-30srv?variant=49392897065153",
                "AddedItemSKU": "810164581259",
                "AddedItemImageURL": "https://cdn.shopify.com/s/files/1/2640/1510/files/StrawMilkfarm.webp?v=1753215138",
                "Product Type": "Protein - Protein Isolates"
            }
        };

        await TectonicKlaviyo.createEvent(eventPayload);
        alert("Event created successfully! Check your Klaviyo dashboard to verify all properties (including arrays and objects) are present.");
        console.log("Event payload sent:", JSON.stringify(eventPayload, null, 2));
    } catch (error) {
        alert(`Failed to create event: ${error.message}`);
        console.error("Error creating event:", error);
    }
}
