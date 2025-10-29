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
