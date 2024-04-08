<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
<head>
  <title>Title</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#Comment_regist').click(function() {
                const bno = '${boardDto.bno}';
                const com_writer = $('#com_writer').val();
                const com_content = $('#com_content').val();


                if(com_writer == ''){
                    alert('로그인 후 이용해주세요');
                    return;
                } else if(com_content == '') {
                    alert('내용을 입력하세요');
                    return;
                }

                $.ajax({
                    type:"POST",
                    url:'<c:url value="/comment/addComment"/>',
                    data: JSON.stringify(
                        {
                            "bno":bno,
                            "commentWriter":com_writer,
                            "commentContent":com_content
                        }
                    ),
                    contentType: 'application/json',
                    success:function(data){
                        console.log('통신성공' + data);
                        if(data == 'success') {
                            alert('댓글 등록이 완료되었습니다.');
                            console.log('댓글 등록 완료');
                            $('#com_writer').val(com_writer);
                            $('#com_content').val('');
                            getList(); // 댓글 목록을 다시 가져오는 함수 호출
                        } else {
                            alert('로그인 이후 이용해주시기 바랍니다.');
                            console.log('댓글 등록 실패');
                        }
                    },
                    error:function(){
                        alert('통신실패');
                    }
                });
            });
        });

        getList();

        $(document).ready(function(){
        function getList() {

          const com_bno = ${boardDto.bno};
          $.getJSON(
                  "<c:url value='/comment/commentList/'/>"+com_bno,
                  function(data) {
                    if(data.total > 0){
                      var list = data.list;

                      var comment_html = "<div>";

                      $('#count').html(data.total);//count에 대입

                      for(i = 0;i < list.length;i++){//list for문으로 출력
                        var cno = list[i].cno;//map list의 commentWriter가져오기
                        var content = list[i].commentContent;//map list의 commentContent가져오기
                        var writer = list[i].commentWriter;//map list의 commentWriter가져오기


                        comment_html += "<div><span id='com_writer'><strong>" + writer + "</strong></span><br/>";
                        comment_html += "<span id='com_content_" + cno + "'>" + content + "</span>";
                        comment_html += "<span class='com_cno' style = display:none>" + cno + "</span><br>";
                        if(writer === $("#com_writer").val()){
                          comment_html += "<span class='modify' style='cursor:pointer;' data-id ='"+content+"'>[수정]</span><br></div><hr>";
                          comment_html += "<span class='delete' style='cursor:pointer;' data-id ='"+cno+"'>[삭제]</span><br></div><hr>";

                        }
                        else{
                          comment_html += "</div><hr>";
                        }
                      }

                      $(".comment_Box").html(comment_html);


                    }
                    else{
                      var comment_html = "<div>등록된 댓글이 없습니다.</div>";
                      $(".comment_Box").html(comment_html);
                    }


                  }

          );

          $(document).on("click", ".modify", function () {
            var cno = $(this).attr("data-id");
            var content = $("#com_content_" + cno).text();
            var inputField = "<input type='text' id='com_content_" + cno + "' value='" + content + "'/>";
            $("#com_content_" + cno).replaceWith(inputField);
            console.log(1);
          });
        }




        $(document).on("click", ".delete", function(){
          /* const com_bno = ${boardDto.bno}; */
          const com_writer = $('#com_writer').val();
          /* const com_content =$('#com-content').text(); */
          const cno = $(this).data('id');
          console.log(cno);


          alert('댓글을 삭제하시겠습니까?');
          console.log('댓글삭제');

          $.ajax({
            type:'delete',
            url:'<c:url value="/comment/deleteComment"/>',
            data:JSON.stringify(
                    {
                      "cno":cno,
                      "commentWriter":com_writer

                    }
            ),
            contentType: 'application/json',
            success:function(data){
              console.log('통신성공'+data);
              alert('댓글이 삭제되었습니다');
              getList();
            },
            error:function(){
              alert('통신실패');
            }
          }); //댓글 삭제 비동기

        });


        <%--$(document).on("click", ".modify", function(){--%>


        <%--  /* const com_bno = ${boardDto.bno}; */--%>
        <%--  const com_writer = $('#com_writer').val();--%>
        <%--  const com_content = $('#com_content').text();--%>
        <%--  const cno = $(this).data('id');--%>



        <%--  alert('댓글을 삭제하시겠습니까?');--%>
        <%--  console.log('댓글삭제');--%>

        <%--  $.ajax({--%>
        <%--    type:'post',--%>
        <%--    url:'<c:url value="/comment/modifyComment"/>',--%>
        <%--    data:JSON.stringify(--%>
        <%--            {--%>
        <%--              "cno":cno,--%>
        <%--              "commentWriter":com_writer,--%>
        <%--              "commentContent":com_content--%>

        <%--            }--%>
        <%--    ),--%>
        <%--    contentType: 'application/json',--%>
        <%--    success:function(data){--%>
        <%--      console.log('통신성공'+data);--%>
        <%--      alert('댓글이 수정되었습니다');--%>
        <%--      getList();--%>
        <%--    },--%>
        <%--    error:function(){--%>
        <%--      alert('통신실패');--%>
        <%--    }--%>
        <%--  }); //댓글 삭제 비동기--%>

        <%--});--%>
    </script>
  <style>
    .comment-box {
      margin-bottom: 20px;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    .comment-count {
      font-size: 18px;
      font-weight: bold;
      margin-bottom: 10px;
    }

    .comment-name {
      margin-bottom: 10px;
    }

    .anonym {
      font-size: 14px;
      font-weight: bold;
    }

    .comment-input {
      width: 100%;
      padding: 10px;
      border: 1px solid #ccc;
      border-radius: 5px;
      margin-bottom: 10px;
      resize: none;
    }

    .regBtn button {
      padding: 10px 20px;
      background-color: #007bff;
      color: #fff;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }

    .regBtn button:hover {
      background-color: #0056b3;
    }

    .comment_Box {
      border: 1px solid #ccc;
      border-radius: 5px;
      padding: 20px;
    }
  </style>
</head>

<body>
<div class="comment-box">

  <div class="comment-count">댓글 <span id="count">0</span></div>

  <!-- <span class="c-icon"><i class="fa-solid fa-user"></i>  -->
  <div class="comment-name">
	                        <span class="anonym">작성자 :
	                    	    <input type="text" class="form-control" id="com_writer" placeholder="이름" name ="commentWriter" value='${id}' readonly  style="width: 100px; border:none;">
	                        </span>
  </div>

  <div class="comment-sbox">
    <textarea class="comment-input" id="com_content" cols="80" rows="2" name="commentContent" ></textarea>

    <div class="regBtn">
      <button id="Comment_regist"> 댓글등록</button>
    </div>
  </div>
</div>
<div class="comment_Box" style="border:1px solid gray;"> <!-- 댓글이 들어갈 박스 -->

</div>

</body>
</html>




