import { initializeApp } from "https://www.gstatic.com/firebasejs/9.6.1/firebase-app.js";
import { getAuth, signInWithEmailAndPassword } from "https://www.gstatic.com/firebasejs/9.6.1/firebase-auth.js";
import { $ } from "./assets/js/utils.js";
import { validateInputTextField } from "./assets/js/validations.js";

const firebaseConfig = {
    apiKey: "AIzaSyDAKQ3E3QZrohOcJxXfeAdFsoUBJpGopS4",
    authDomain: "java-firebase-30555.firebaseapp.com",
    projectId: "java-firebase-30555",
    storageBucket: "java-firebase-30555.appspot.com",
    messagingSenderId: "43156232986",
    appId: "1:43156232986:web:6057237a73afc325556aa3"
  };

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);




$('.form__input-group-input', true).forEach(e => {
    e.addEventListener('input', ({ target }) => validateInputTextField(target));
});




$('.form').addEventListener('submit', async e => {
    e.preventDefault();
    const target = e.target;
    let hasError = false;
    for (const child of target) {
        if (child.tagName?.toLowerCase() !== 'input') continue;
        if (!validateInputTextField(child)) hasError = true;
    }

    if (hasError) return;
    
    const accessToken = await sign(target['email'].value, target['password'].value);
    await receiveBackendMessage(accessToken);
});

async function sign(email, password) {
    try {
        const { user: { accessToken } } = await signInWithEmailAndPassword(auth, email, password);
        return accessToken;
    } catch (error) {
        console.log(error);
    }
}

async function receiveBackendMessage(token) {
    try {
        console.log(token)
        const req = await fetch('http://localhost:8080/', {
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });
        const json = req.json();
        console.log(json);
    } catch (error) {
        console.log(error);
    }
}