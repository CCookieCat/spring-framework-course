<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<script type="text/javascript">
    function onLoad() {
        $("#password").keyup(checkPasswordsMatch);
        $("#confirmpass").keyup(checkPasswordsMatch);

        $("#details").submit(canSubmit);
    }

    function canSubmit() {
        var password = $("#password").val();
        var confirmpass = $("#confirmpass").val();

        if(password != confirmpass) {
            alert("<fmt:message key='UmatchedPasswords.user.password'/>")
            return false;
        }
        else {
            return true;
        }
    }

    function checkPasswordsMatch() {
        var password = $("#password").val();
        var confirmpass = $("#confirmpass").val();

        if(password.length > 3 || confirmpass.length > 3) {
            if(password == confirmpass) {
                $("#matchpass").text("<fmt:message key='MatchedPasswords.user.password'/>");
                $("#matchpass").addClass("valid");
                $("#matchpass").removeClass("error");
            }
            else {
                $("#matchpass").text("<fmt:message key='UmatchedPasswords.user.password'/>");
                $("#matchpass").addClass("error");
                $("#matchpass").removeClass("valid");
            }
        }
    }

    $(document).ready(onLoad);
</script>