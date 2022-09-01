<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<h2> Here are your messages </h2>

<div id="messages"></div>

<!-- Displaying Messages: -->
<script type="text/javascript">

    var timer;

    $(document).ready(onLoad);

    function onLoad() {
        console.log("Timer started.");
        updatePage();
        startTimer();
    }

    function updatePage() {
        console.log("Page has been updated!");
        var messagesServerUrl = "<c:url value='/getmessages'/>"
        $.getJSON(messagesServerUrl, updateMessages);

    }

    function showReply(i) {
        stopTimer();
        $("#form"+i).toggle();
    }

    function startTimer() {
        timer = window.setInterval(updatePage,5000);
    }

    function stopTimer() {
        window.clearInterval(timer);
    }

    function sendMessage(i, name, email) {
        var msg = $("#textbox"+i).val();
        console.log(msg + "Message sent" + i);
        console.log(name + email);

        $.ajax({
            'type': 'POST',
            'url': '<c:url value="/sendmessage" />',
            'data': JSON.stringify({'target':i, 'text':msg, 'name':name, 'email':email}),
            'success': messageSent,
            'error': messageError,
            contentType: "application/json",
            dataType: "json",
            headers: { 'X-CSRF-Token': $('meta[name="_csrf"]').attr('content') }
        });
    }

    function messageSent(data){
        startTimer();
        $("#form"+data.target).toggle();
        $("#alert"+data.target).text("Message has been sent.");
    }
    function messageError(data){
        alert("An error occurred when trying to send the message. Please try again later.");
    }

    function updateMessages(data) {
        $("#messages").html("");

        for(var i=0; i<data.messages.length; i++) {
            var message = data.messages[i];

            var messageDiv = document.createElement("div");
            messageDiv.setAttribute("class", "message");

            var nameSpan = document.createElement("span");
            nameSpan.setAttribute("class", "sender");
            nameSpan.appendChild(document.createTextNode(message.sender + "(" ));
            var link = document.createElement("a");
            link.setAttribute("class", "replylink");
            link.setAttribute("href", "#");
            link.setAttribute("onclick", "showReply(" + i + ")" );
            link.appendChild(document.createTextNode(message.email));
            nameSpan.appendChild(link);
            nameSpan.appendChild(document.createTextNode( ")" ));

            var subjectSpan = document.createElement("span");
            subjectSpan.setAttribute("class", "subject");
            subjectSpan.appendChild(document.createTextNode(message.subject));

            var contentSpan = document.createElement("span");
            contentSpan.setAttribute("class", "messagebody");
            contentSpan.appendChild(document.createTextNode(message.content));

            var replyForm = document.createElement("form");
            replyForm.setAttribute("class", "replyform");
            replyForm.setAttribute("id", "form" + i);
            var textarea = document.createElement("textarea");
            textarea.setAttribute("class", "replyarea");
            textarea.setAttribute("id", "textbox" + i);

            var replyButton = document.createElement("input");
            replyButton.setAttribute("type", "button");
            replyButton.setAttribute("value", "Reply");
            replyButton.setAttribute("class", "replybutton");
            replyButton.onclick = function(j, name, email) {
                return function() {
                    sendMessage(j, name, email);
                };
            }(i, message.sender, message.email);

            alertSpan = document.createElement("span");
            alertSpan.setAttribute("class", "alert");
            alertSpan.setAttribute("id", "alert"+i);


            replyForm.appendChild(textarea);
            replyForm.appendChild(replyButton);

            messageDiv.appendChild(subjectSpan);
            messageDiv.appendChild(nameSpan);
            messageDiv.appendChild(alertSpan);
            messageDiv.appendChild(contentSpan);
            messageDiv.appendChild(replyForm);

            $("#messages").append(messageDiv);

        }
    }
</script>