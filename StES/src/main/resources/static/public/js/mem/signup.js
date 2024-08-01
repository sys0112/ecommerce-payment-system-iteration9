const signup = document.forms["signup"];
const checkEmail = document.getElementById("checkEmail");
const checkPhone = document.getElementById("checkPhone");
const checkPw = document.getElementById("checkPw");
const checkPwCheck = document.getElementById("checkPwCheck");

// Variables to track validation state
let emailValid = false;
let phoneValid = false;
let pwValid = false;
let pwCheckValid = false;

// Email validation
signup["email"].addEventListener("blur", async (e) => {
    let v = e.target.value;
    let emailEx = /([0-9a-zA-Z._%+-]+@[0-9a-zA-Z.-]+\.[a-zA-Z]{2,3})/;
    let res = await fetch("/mem/ajax/findEmail/" + v);
    let json = await res.json();
    if (json) {
        checkEmail.innerText = "이미 사용중인 이메일입니다.";
        checkEmail.classList.remove("text-primary");
        checkEmail.classList.add("text-danger");
        emailValid = false;
    } else {
        if (emailEx.test(v)) {
            checkEmail.innerText = "사용가능";
            checkEmail.classList.remove("text-danger");
            checkEmail.classList.add("text-primary");
            emailValid = true;
        } else {
            checkEmail.innerText = "이메일 형식이 맞지않습니다.";
            checkEmail.classList.remove("text-primary");
            checkEmail.classList.add("text-danger");
            emailValid = false;
        }
    }
});

// Phone number formatting
        function formatPhoneNumber(value) {
            // Remove all non-digit characters
            value = value.replace(/\D/g, '');

            // Format the number
            if (value.length <= 3) {
                return value;
            } else if (value.length <= 7) {
                return value.slice(0, 3) + '-' + value.slice(3);
            } else {
                return value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11);
            }
        }

        // Phone number validation
        signup["phone"].addEventListener("input", (e) => {
            // Format phone number
            let formattedValue = formatPhoneNumber(e.target.value);
            e.target.value = formattedValue;
        });

        signup["phone"].addEventListener("blur", async (e) => {
            let v = e.target.value;
            let phoneEx = /^01(0|1|6|9)-(\d{3,4})-(\d{4})$/;
            if (phoneEx.test(v)) {
                let res = await fetch("/mem/ajax/findPhone/" + v);
                let json = await res.json();
                if (json) {
                    checkPhone.classList.add("text-danger");
                    checkPhone.innerText = "이미 사용중인 번호입니다.";
                    phoneValid = false;
                } else {
                    checkPhone.classList.remove("text-danger");
                    checkPhone.classList.add("text-primary");
                    checkPhone.innerText = "사용가능한 번호입니다.";
                    phoneValid = true;
                }
            } else {
                checkPhone.classList.remove("text-primary");
                checkPhone.classList.add("text-danger");
                checkPhone.innerText = "형식이 맞지않습니다.";
                phoneValid = false;
            }
        });

// Password length validation
signup["pw"].addEventListener("blur", () => {
    let v = signup["pw"].value;
    if (v.length >= 4) {
        checkPw.innerText = "길이가 4이상";
        checkPw.classList.remove("text-danger");
        checkPw.classList.add("text-primary");
        pwValid = true;
    } else {
        checkPw.innerText = "비밀번호는 4자 이상이어야 합니다.";
        checkPw.classList.remove("text-primary");
        checkPw.classList.add("text-danger");
        pwValid = false;
    }
});

// Password confirmation validation
signup["pw_check"].addEventListener("blur", () => {
    let pw = signup["pw"].value;
    let pw_check = signup["pw_check"].value;
    if (pw === pw_check) {
        checkPwCheck.innerText = "비밀번호가 일치합니다.";
        checkPwCheck.classList.remove("text-danger");
        checkPwCheck.classList.add("text-primary");
        pwCheckValid = true;
    } else {
        checkPwCheck.innerText = "비밀번호가 일치하지 않습니다.";
        checkPwCheck.classList.remove("text-primary");
        checkPwCheck.classList.add("text-danger");
        pwCheckValid = false;
    }
});

// Form submission validation
signup.addEventListener("submit", (e) => {
    // Check all validation states
    if (!emailValid || !phoneValid || !pwValid || !pwCheckValid) {
        e.preventDefault(); // Prevent form submission
        alert("모든 필드를 올바르게 입력해야 합니다."); // Alert the user
    }
});