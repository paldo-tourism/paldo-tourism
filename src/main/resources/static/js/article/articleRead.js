var user_id = $('#input-user-id').val();
var board_id = $('#input-board-id').val();
var comment_page = $('#input-comment-page').val();

$(document).ready(function(){

  // let board_id = [[${article.id}]];

  // 글 좋아요
  function addArticleLike(){
    $.ajax({
      url: '/reaction/board/like',
      method: 'post',
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        boardId: board_id,
        userId: user_id
      }),
      success: function(result){
        console.log("글 좋아요 성공" + result)
        $("#icon-article-like").attr("src", "/icon/thumb-up-fill.png");
        $("#btn-article-like").attr('class',"article-like-cancel");
        $(".count-article-like").html(result);
      }
    })
  }

  function subArticleLike(){
    $.ajax({
      url: '/reaction/board/like',
      method: 'delete',
      contentType: "application/json; charset=utf-8",
      data: JSON.stringify({
        boardId: board_id,
        userId: user_id
      }),
      success: function(result){
        console.log("글 좋아요 취소 성공" + result)
        $("#icon-article-like").attr("src", "/icon/thumb-up.png");
        $("#btn-article-like").attr('class',"article-like");
        $(".count-article-like").html(result);
      }
    })
  }

  $(document).on("click", ".article-like", addArticleLike);
  $(document).on("click", ".article-like-cancel", subArticleLike);

  //글 삭제
  function deleteArticle(){
    if(!confirm('삭제하시면 복구할수 없습니다. \n 정말로 삭제하시겠습니까??')){
      return false;
    }

    $.ajax({
      url:"/api/board/" + board_id,
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
    console.log(content, board_id, user_id);

    $.ajax({
      url: '/comment/write',
      method: 'POST',
      contentType : 'application/json; charset=utf-8',
      data: JSON.stringify({
        content: content,
        board_id: board_id,
        user_id: user_id
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

    var parent_id = $parentComment.children('.comment-id').val();
    var parent_depth = $parentComment.data('depth');
    var content = $(this).prev().val();

    console.log(parent_id + " / " + content + "/" + parent_depth + " / ");

    $.ajax({
      url : '/comment/write',
      method: 'post',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({
        parent_id: parent_id,
        content: content,
        board_id: board_id,
        user_id: user_id
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

    var comment_id = $(this).closest('.list-item').children('.comment-id').val();
    var content = $('#input-modify').val();

    console.log(comment_id + " / " + content);

    $.ajax({
      url : '/comment/update',
      method: 'put',
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify({
        id: comment_id,
        content: content,
        user_id: user_id
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
    var getComment_id = $(this).closest('.list-item').children('.comment-id').val();

    console.log(getComment_id);

    $.ajax({
      url: '/comment/delete',
      method: 'delete',
      data: {
        comment_id : getComment_id
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
      board_id : board_id,
      page : comment_page
    };

    console.log(loadRequestInfo)

    $.ajax({
      url: '/comment/list',
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

  function addLike (){
    var comment_id = $(this).data('comment-id');

    var likeRequest ={
      comment_id : comment_id,
      user_id : user_id
    }

    $.ajax({
      url: '/reaction/comment/like',
      method: 'post',
      data: JSON.stringify(likeRequest),
      contentType : 'application/json; charset=utf-8',
      dataType : "json",
      success: function(data){
        console.log("좋아요 성공" + data);
        $('[data-comment-id='+comment_id+']').children('.icon-like').attr("src", "/icon/thumb-up-fill.png");
        $(".count-like-"+comment_id).html(data);
        $('[data-comment-id='+comment_id+']').attr('class',"btn-like-cancel");
      }
    });
  }

  function subLike (){
    var comment_id = $(this).data('comment-id');

    var likeRequest ={
      comment_id : comment_id,
      user_id : user_id
    }

    $.ajax({
      url: '/reaction/comment/like',
      method: 'delete',
      data: JSON.stringify(likeRequest),
      contentType : 'application/json; charset=utf-8',
      dataType : "json",
      success: function(data){
        console.log("좋아요 취소 성공" + data);
        $('[data-comment-id='+comment_id+']').children('.icon-like').attr("src", "/icon/thumb-up.png");
        $(".count-like-"+comment_id).html(data);
        $('[data-comment-id='+comment_id+']').attr('class',"btn-like");
      }
    });
  }

  // .on으로 이후 추가되는 요소에도 이벤트 연결해줌
  $(document).on('click', '.btn-like', addLike);
  $(document).on('click', '.btn-like-cancel', subLike);


  function pageMove() {

    var loadRequestInfo = {
      board_id : board_id,
      page : $(this).data("page")
    };

    $.ajax({
      url: '/comment/list',
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
      board_id : board_id,
      page : [[${commentResult.start - 1}]]
    };

    $.ajax({
      url: '/comment/list',
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
      board_id : board_id,
      page : [[${commentResult.end + 1}]]
    };

    $.ajax({
      url: '/comment/list',
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