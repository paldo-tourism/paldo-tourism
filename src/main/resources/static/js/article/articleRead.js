var articleId = $('#input-board-id').val();
var commentPage = $('#input-comment-page').val();

$(document).ready(function(){

  //글 삭제
  function deleteArticle(){
    if(!confirm('삭제하시면 복구할수 없습니다. \n 정말로 삭제하시겠습니까??')){
      return false;
    }

    $.ajax({
      url:"/article/" + articleId,
      method: "delete",
      success(){
        console.log("삭제 성공");
        location.href
      }
    })
  }

  $(document).on("click","#btn-delete-article",deleteArticle);

  // 댓글 등록 버튼
  $('#form-comment').submit(function(event) {
    event.preventDefault(); // 폼 기본 동작 방지
    var content = $('#input-comment').val(); // 입력된 댓글 텍스트 가져오기
    console.log(content, articleId);

    $.ajax({
      url: '/comment/write',
      method: 'POST',
      contentType : 'application/json; charset=utf-8',
      data: JSON.stringify({
        content: content,
        articleId: articleId
      }),
      success: function() {
        // 댓글 등록 성공 시, 목록 다시 불러오기
        console.log("댓글 등록 성공")
        $('#input-comment').val(''); // 입력 필드 비우기
      }
    }).done(loadComment);
  });

  //토글 입력폼 나타내기 버튼
  function displayReplyForm (){
    $('#form-reply').appendTo($(this).parent().parent().parent()).toggle();
  }

  $(document).on('click', '.btn-reply-toggle', displayReplyForm);

  //대댓글 등록 버튼
  function writeReply(){

    var $parentComment = $(this).closest('.list-item');

    var parentId = $parentComment.children('.comment-id').val();
    var parent_depth = $parentComment.data('depth');
    var content = $(this).prev().val();

    console.log(parentId + " / " + content + "/" + parent_depth + " / ");

    $.ajax({
      url : '/comment/write',
      method: 'post',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({
        parentId: parentId,
        content: content,
        articleId: articleId
      }),
      success: function(){
        console.log("대댓글 등록 성공")
        $("#form-reply").css("display","none").appendTo("body");
        $("#input-reply").val("");
      }
    }).done(loadComment);
  }

  $(document).on('click', '#btn-reply-submit', writeReply);

  //수정 버튼
  function displayModifyForm (){
    var originalContent = $(this).closest('.list-item').children('.comment-content').val();

    $('#input-modify').val(originalContent);
    $('#form-modify').appendTo($(this).parent().parent().parent().parent()).toggle();
  }
  $(document).on('click', '.btn-modify', displayModifyForm)

  function modifyComment(){

    var commentId = $(this).closest('.list-item').children('.comment-id').val();
    var author = $(this).closest('.list-item').find('.comment-author').text();
    var content = $('#input-modify').val();

    console.log(commentId + " / " + content + "/" + author );

    $.ajax({
      url : '/comment/' + commentId,
      method: 'put',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({
        articleId: articleId,
        content: content,
        author: author
      }),
      success: function(){
        console.log("댓글 수정 성공")
        $("#form-modify").css("display","none").appendTo("body");
        $("#input-modify").val("");
      }
    }).done(loadComment);
  }

  $(document).on('click', '#btn-modify-submit', modifyComment);


  // 삭제 버튼
  function deleteComment(){
    var commentId = $(this).closest('.list-item').children('.comment-id').val();
    var author = $(this).closest('.list-item').find('.comment-author').text();

    console.log(commentId + author);

    $.ajax({
      url: '/comment/' + commentId,
      method: 'delete',
      data: {
        articleId : articleId,
        author : author
      },
      success: function (){
        console.log("삭제 성공")
      }
    }).done(loadComment);
  }

  $(document).on('click', '.btn-delete', deleteComment);

  //새로고침
  function loadComment() {
    var loadRequestInfo = {
      articleId : articleId,
      page : [[${commentList.page}]]
    };

    console.log(loadRequestInfo)

    $.ajax({
      url: '/comment',
      method: 'get',
      data: loadRequestInfo,
      success: function () {
        console.log("새로고침 성공");
      }
    })
        .done(function (fragment){
          $('#all-comment').replaceWith(fragment);
        });
  }

  // 댓글 새로고침 버튼 클릭 이벤트
  $('#btn-refresh').click(function() {
    loadComment();
  });

  function pageMove() {

    var loadRequestInfo = {
      articleId : articleId,
      page : $(this).data("page")
    };

    $.ajax({
      url: '/comment',
      method: 'get',
      data: loadRequestInfo,
      success: function () {
        console.log("페이지 이동 성공");
      }
    })
        .done(function (fragment){
          $('#all-comment').replaceWith(fragment);
        });
  }

  $(document).on("click", ".comment-page-number", pageMove)

  // 이전 페이지
  function prevMove() {
    var loadRequestInfo = {
      articleId : articleId,
      page : [[${commentList.start - 2}]]
    };

    $.ajax({
      url: '/comment',
      method: 'get',
      data: loadRequestInfo,
      success: function () {
        console.log("이전페이지 이동 성공");
      }
    })
        .done(function (fragment){
          $('#all-comment').replaceWith(fragment);
        });
  }

  $(document).on("click", "#comment-page-prev", prevMove)

  // 다음 페이지
  function nextMove() {
    var loadRequestInfo = {
      articleId : articleId,
      page : [[${commentList.end}]]
    };

    $.ajax({
      url: '/comment',
      method: 'get',
      data: loadRequestInfo,
      success: function () {
        console.log("다음페이지 이동 성공");
      }
    })
        .done(function (fragment){
          $('#all-comment').replaceWith(fragment);
        });
  }

  $(document).on("click", "#comment-page-next", nextMove)

})