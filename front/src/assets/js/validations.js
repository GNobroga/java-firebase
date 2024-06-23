export function validate(fieldName, value) {
    const validations = {
        email(value) {
            return [
                (!value && value?.trim() === '') && "O campo é obrigatório", 
                !/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/.test(value) && "E-mail não é válido.",
            ];
        },
        password(value) {
            return [
                (!value && value?.trim() === '') && "O campo é obrigatório", 
            ];
        },
    }

    const errors = validations[fieldName] ? validations[fieldName](value) : [];
    return errors.filter(error => typeof error === 'string')[0];
}


export function validateField(target) {
    const { name, value } = target;
    const sibling = target?.nextElementSibling;
    const errorMessage = validate(name, value);
    !errorMessage ? target.classList.remove('form__input-group-input--error') : target.classList.add('form__input-group-input--error');
    if (sibling?.textContent != null) {
        sibling.textContent = errorMessage;
        return false;
    }
    return true;
}

