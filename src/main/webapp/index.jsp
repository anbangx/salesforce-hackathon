<%@ page language="java" contentType="text/html; charset=ISO-8859-1"  
    pageEncoding="ISO-8859-1"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<html>    
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">  
<title>Insert title here</title>  
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script type="text/javascript">  
    $(document).ready(function(){   
            $('#getData').click(function(){  
                $.ajax({  
                    url:'JsonServlet',  
                    type:'post',  
                    dataType: 'json',  
                    success: function(data) {  
                        $('#name').val(data.name);  
                        $('#email').val(data.email);  
                    }  
                });  
            });  
    });  
</script>  
</head>  
<body>  
  
    Name:<input type="text" id="name"/><br/>  
    Email:<input type="text" id="email"/>  
      
    <input type="button" id="getData" value="Get Data"/>   
  
</body>  
</html> 
