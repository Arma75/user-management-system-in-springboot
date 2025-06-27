document.getElementById('signupForm').addEventListener('submit', async function(event) {
    event.preventDefault(); // 기본 폼 제출 동작을 막습니다.

    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');
    const submitButton = document.getElementById('submitButton');
    const globalMessageElement = document.getElementById('global-message');
    const usernameErrorElement = document.getElementById('username-error');
    const passwordErrorElement = document.getElementById('password-error');

    // 메시지 초기화
    globalMessageElement.textContent = '';
    globalMessageElement.className = 'message';
    usernameErrorElement.textContent = '';
    passwordErrorElement.textContent = '';

    const username = usernameInput.value.trim(); // 공백 제거
    const password = passwordInput.value.trim(); // 공백 제거

    let isValid = true;

    // 클라이언트 측 유효성 검사 (기본적인 HTML5 required와 minlength는 브라우저가 처리하지만, JS로도 한 번 더)
    if (!username) {
        usernameErrorElement.textContent = '아이디를 입력해주세요.';
        isValid = false;
    } else if (username.length < 4 || username.length > 20) {
        usernameErrorElement.textContent = '아이디는 4~20자여야 합니다.';
        isValid = false;
    }

    if (!password) {
        passwordErrorElement.textContent = '비밀번호를 입력해주세요.';
        isValid = false;
    } else if (password.length < 8) {
        passwordErrorElement.textContent = '비밀번호는 최소 8자 이상이어야 합니다.';
        isValid = false;
    }
    // 더 복잡한 비밀번호 유효성 검사 (예: 영문, 숫자, 특수문자 포함)도 여기에 추가 가능

    if (!isValid) {
        globalMessageElement.textContent = '입력 양식을 다시 확인해주세요.';
        globalMessageElement.classList.add('error');
        return; // 유효성 검사 실패 시 API 호출 중단
    }

    // 제출 버튼 비활성화 및 로딩 표시
    submitButton.disabled = true;
    submitButton.textContent = '가입 중...';
    globalMessageElement.textContent = '회원가입 요청 중...';
    globalMessageElement.classList.add('info'); // 임시 정보 스타일 (CSS에 추가 필요)

    try {
        const response = await fetch('/api/users/simple-register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        const responseText = await response.text(); // 응답을 텍스트로 받음

        if (response.ok) { // HTTP 상태 코드가 200번대인 경우 (성공)
            globalMessageElement.textContent = '회원가입 성공: ' + responseText;
            globalMessageElement.classList.add('success');
            // 성공 시 입력 필드 초기화
            usernameInput.value = '';
            passwordInput.value = '';
            usernameErrorElement.textContent = ''; // 성공 시 에러 메시지 초기화
            passwordErrorElement.textContent = ''; // 성공 시 에러 메시지 초기화

        } else { // HTTP 상태 코드가 200번대가 아닌 경우 (실패)
            let errorMessage = '회원가입 실패: ' + responseText;
            if (response.status === 409) { // 409 Conflict는 보통 중복 아이디
                errorMessage = '회원가입 실패: 이미 사용 중인 아이디입니다.';
                usernameErrorElement.textContent = '이미 사용 중인 아이디입니다.'; // 특정 필드에 에러 표시
            } else if (response.status === 400) { // 400 Bad Request는 유효성 검사 실패 등
                errorMessage = '회원가입 실패: 입력 값이 올바르지 않습니다.';
            }
            globalMessageElement.textContent = errorMessage;
            globalMessageElement.classList.add('error');
            console.error('회원가입 API 오류:', response.status, responseText);
        }
    } catch (error) {
        console.error('네트워크 오류:', error);
        globalMessageElement.textContent = '네트워크 오류가 발생했습니다. 서버가 실행 중인지 확인해주세요.';
        globalMessageElement.classList.add('error');
    } finally {
        // API 호출 완료 후 버튼 다시 활성화
        submitButton.disabled = false;
        submitButton.textContent = '가입하기';
    }
});