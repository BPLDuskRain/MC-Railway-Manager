function submitForm(){

    const formData = new FormData(document.getElementById('lineForm'));

    axios.post(`${url}/userCheck`, formData)
        .then(response =>{
            const data = response.data;
            if(data === true){
                document.getElementById('res').textContent = '管理员登陆成功喵！一秒后将跳转回原页面喵！';
                setTimeout(() => {
                    window.close();
                }, 1000);
            }
            else{
                document.getElementById('res').textContent = '用户名或密码错误了喵！一秒后将跳转回原页面喵！';
                setTimeout(()=>{
                    window.close();
                }, 1000); 
            }
        })
        .catch(error => {
            document.getElementById('res').textContent = '数据库故障了喵！';
        })
}