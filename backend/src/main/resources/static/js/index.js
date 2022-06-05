//立即执行函数
(function (doc,Socket) {
    const oMsg = doc.querySelector('#message');
    const oSendBtn = doc.querySelector('#send');
    const url ='ws:localhost:8081/api/websocket/'+oMsg.getAttribute('value');
    const ws = new Socket(url);

    //初始化函数
    const init =() =>{
        bindEvent();

    }
    function bindEvent() {
        oSendBtn.addEventListener('click',handleSendBtnClick,false);
        ws.addEventListener('open',handleOpen,false);
        ws.addEventListener('close',handleClose,false);
        ws.addEventListener('error',handleError,false);
        ws.addEventListener('message',handleMessage,false);
    }



    function handleSendBtnClick() {
        //实际业务每一分钟或者30秒发送一次心跳，用来检测服务是否挂掉
        ws.send(1);
        console.log('Send message');
    }

    function handleOpen(e) {
        console.log('Websocket open',e)
    }

    function handleClose(e) {
        console.log('Websocket close',e)
    }

    function handleError(e) {
        console.log('Websocket error',e)
    }

    function handleMessage(e) {
        console.log('Websocket message',e);
        console.log('接收到的具体内容=',e.data)
    }

    init();
})(document, WebSocket);
