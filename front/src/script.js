import { validateField } from "./assets/js/validations.js";
import { $ } from "./assets/js/utils.js";
import { initializeApp} from "./assets/js/firebase-app.js";
// import firebaseAuth from "./assets/js/firebase-auth.js";

const firebaseConfig = {
    apiKey: "AIzaSyDAKQ3E3QZrohOcJxXfeAdFsoUBJpGopS4",
    authDomain: "java-firebase-30555.firebaseapp.com",
    projectId: "java-firebase-30555",
    storageBucket: "java-firebase-30555.appspot.com",
    messagingSenderId: "43156232986",
    appId: "1:43156232986:web:6057237a73afc325556aa3"
  };

const app = initializeApp(firebaseConfig)

console.log(app)

$('.form__input-group-input', true).forEach(e => {
    e.addEventListener('input', ({ target }) => validateField(target));
});


$('.form').addEventListener('submit', e => {
    e.preventDefault();
    const target = e.target;
    for (const child of target) {
        if (child.tagName?.toLowerCase() !== 'input') continue;
        validateField(child);
    }

});

