import { TectonicKlaviyo } from '@tectonic-technologies/klaviyo-capacitor-plugin';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    TectonicKlaviyo.echo({ value: inputValue })
}
