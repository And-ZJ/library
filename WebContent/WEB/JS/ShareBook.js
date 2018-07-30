/**
 * 
 */

function showRating(averageScoreStr)
{
	var averageScore = parseFloat(averageScoreStr,10);
	//alert(averageScore);
	if (averageScore > 0.25)
	{
		//alert("test1");
		jQuery("img#rating_1").attr("src","SOURCE/images/red_half_rating.png");
	}
	
	if (averageScore > 0.75)
	{
		//alert("test2");
		jQuery("img#rating_1").attr("src","SOURCE/images/red_full_rating.png");
	}
	if (averageScore > 1.25)
	{
		jQuery("img#rating_2").attr("src","SOURCE/images/red_half_rating.png");
	}
	if (averageScore > 1.75)
	{
		jQuery("img#rating_2").attr("src","SOURCE/images/red_full_rating.png");
	}
	if (averageScore > 2.25)
	{
		jQuery("img#rating_3").attr("src","SOURCE/images/red_half_rating.png");
	}
	if (averageScore > 2.75)
	{
		jQuery("img#rating_3").attr("src","SOURCE/images/red_full_rating.png");
	}
	if (averageScore > 3.25)
	{
		jQuery("img#rating_4").attr("src","SOURCE/images/red_half_rating.png");
	}
	if (averageScore > 3.75)
	{
		jQuery("img#rating_4").attr("src","SOURCE/images/red_full_rating.png");
	}
	if (averageScore > 4.25)
	{
		jQuery("img#rating_5").attr("src","SOURCE/images/red_half_rating.png");
	}
	if (averageScore > 4.75)
	{
		jQuery("img#rating_5").attr("src","SOURCE/images/red_full_rating.png");
	}
}
	


function searchBookByIsbn(bookIsbn)
{
	jQuery.ajax({
		type: "POST",
		url: "searchBookAction",
		data:{
			"mode":"isbn",
			 "search_words":bookIsbn
		},
		success:function(data){
			if (data.info == "Match" )
			{
				var bookImageAddress = data.data[0].bookImageAddress
			   	if (bookImageAddress && typeof bookImageAddress != "undefined")
			   	{
			   	    jQuery("img#book_image").attr("src",getFileServerAddress() + bookImageAddress);
			   	}
				jQuery("span#book_isbn").text(data.data[0].bookIsbn);
			   	jQuery("span#book_name").text(data.data[0].bookName);
			    jQuery("span#book_author").text(data.data[0].bookAuthor);
			   	jQuery("span#book_price").text(data.data[0].bookPrice);
			   	jQuery("span#book_publish_company").text(data.data[0].bookPublishCompany);
			   	jQuery("span#book_publish_time").text(data.data[0].bookPublishTime);
			   	jQuery("span#book_summary").text(data.data[0].bookSummary);
				
				var averageScore = data.data[0].bookAverageScore;
				showRating(averageScore);
				jQuery("span#book_score_number").text(data.data[0].bookScoreNumber);
			}
			else if (data.info == "NotMatch")
			{
				alert("抱歉,没有找到这本书.");
			}
			else
			{
				alert("返回信息出错:" + data.info );
			}
		},
		error:function(jQXHR){alert("查询失败,错误码:" + jQXHR.status);}
	});
}


function getRatingHtml(scoreStr)
{
	var score = parseFloat(scoreStr,10);
	if (score <=0.25)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 0.25 && score <=0.75)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_half_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 0.75 && score <=1.25)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 1.25 && score <= 1.75)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_half_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 1.75 && score <= 2.25)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 2.25 && score <= 2.75)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_half_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 2.75 && score <= 3.25)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 3.25 && score <= 3.75)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_half_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 3.75 && score <= 4.25)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/black_empty_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 4.25 && score <= 4.75)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_half_rating.png' class='rating'>";
		return imgHtml;
	}
	else if (score > 4.75)
	{
		var imgHtml = "<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>" +
				"<img alt='图片加载失败' src='SOURCE/images/red_full_rating.png' class='rating'>";
		return imgHtml;
	}
}

function getCommentHtml(scoreAccountName,commentScore)
{
	var commentHtml = 
		"<div class='comment'>" +
			"<div class='score_div'>" +
				"<div class='account_name' align='left'>" + scoreAccountName + "</div>" +
				"<div class='score' align='right'>" + getRatingHtml(commentScore) + "</div>" +
			"</div>" +
			"<div class='content_box'>" + "</div>" +
		"</div>";
	return commentHtml;
}

function getContentHtml(content,time)
{
	var contentHtml = 
		"<div class='content_div' align='left'>" +
			"<div class='content'>" + content + "</div> " +
			"<div class='time'>"+ time +"</div>" +
		"</div>";
	return contentHtml;    
}

function searchCommentContentByIsbn(bookIsbn)
{
	
	jQuery.ajax({
		type: "POST",
		url: "searchCommentAction",
		data:{
			"mode":"isbn",
			 "search_words":bookIsbn
		},
		success:function(data){
			if (data.info == "Match" )
			{
				var size = data.size;
				var commentInformation = data.data;
				jQuery("div#comment_box").children().each(function(){
					var accountName = jQuery(this).find("div.account_name").text();
					//alert(accountName);
					for (var i=0;i<size;i++)
					{  
						var commentAccountName = commentInformation[i].commentAccountName;
						if (commentAccountName == accountName)
						{
							var content = commentInformation[i].commentContent;
							var time = commentInformation[i].commentTime;
							jQuery(this).find("div.content_box").append(getContentHtml(content, time));
						}
					}
				});
			}
			else if (data.info == "NotMatch")
			{
				alert("NotMatch");
			}
			else
			{
				alert("返回信息出错:" + data.info );
			}
		},
		error:function(jQXHR){alert("查询失败,错误码:" + jQXHR.status);}
	});
	
}

function searchCommentByIsbn(bookIsbn)
{
	jQuery.ajax({
		type: "POST",
		url: "searchScoreAction",
		data:{
			"mode":"isbn",
			 "search_words":bookIsbn
		},
		success:function(data){
			if (data.info == "Match" )
			{
				var size = parseInt(data.size);
				if (size > 0)
				{
					for (var i=0;i<size;i++)
					{
						var scoreAccountName = data.data[i].scoreAccountName;
						var commentScore = data.data[i].commentScore;
						jQuery("div#comment_box").append(getCommentHtml(scoreAccountName, commentScore));
					}
					searchCommentContentByIsbn(bookIsbn);
				}
			}
			else if (data.info == "NotMatch")
			{
				alert("NotMatch");
			}
			else
			{
				alert("返回信息出错:" + data.info );
			}
		},
		error:function(jQXHR){alert("查询失败,错误码:" + jQXHR.status);}
	});
}

function loadPage()
{
	jQuery(document).ready(function(){
		var bookIsbn = decodeURI(GetQueryString("book_isbn"));
		//alert(bookIsbn);
		var isOpen = false;
		if (!bookIsbn || typeof bookIsbn == "undefined" || bookIsbn == 0)
		{
			alert("地址出现了问题,请返回重试");
		}
		else
		{
			searchBookByIsbn(bookIsbn);
			searchCommentByIsbn(bookIsbn);
			
			jQuery("div#comment_btn").click(function(){
				if (isOpen)
				{
					jQuery("div#comment_box").hide();
					jQuery(this).text("展开此书相关评论");
					isOpen = false;
				}
				else 
				{
					jQuery("div#comment_box").show();
					jQuery(this).text("收起图书评论");
					isOpen = true;
				}
			});
		}
		
	});
}


	
	
