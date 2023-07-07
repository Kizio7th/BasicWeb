let mybutton = document.getElementById("backtoheader");
window.onscroll = function () {
  scrollFunction();
};
function scrollFunction() {
  if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
    mybutton.style.display = "block";
  } else {
    mybutton.style.display = "none";
  }
}
function topFunction() {
  document.body.scrollTop = 0;
  document.documentElement.scrollTop = 0;
}

// ---------------------------------------

// ---------------------------------------

function previewImage(event) {
  var file = event.target.files[0];
  if (file.size > 1024 * 1024) {
    alert("Chọn hình ảnh dưới 1MB");
    return;
  }

  var reader = new FileReader();
  reader.onload = function () {
    var preview = document.getElementById("thumbnail");
    preview.src = reader.result;
  }
  reader.readAsDataURL(file);
}

function validateForm() {
  const thumbnail = document.getElementById("thumbnail");
  if (thumbnail.getAttribute("src") === "data:image/png;base64,null") {
    alert("Hãy upload bìa sách");
    return false;
  }

  return true;
}
//-------------------------------------------------
function searchByName() {
  var input, filter, ul, li, a, p, i, txtValue;
  input = document.getElementById("searchInput");
  filter = input.value.toUpperCase();
  ul = document.getElementById("bookList");
  li = ul.getElementsByTagName("li");
  for (i = 0; i < li.length; i++) {
    a = li[i].getElementsByTagName("h3")[0];
    p = li[i].getElementsByTagName("p")[0];
    txtValue = a.textContent || a.innerText;
    txtValue += p.textContent || p.innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      li[i].style.display = "";
    } else {
      li[i].style.display = "none";
    }
  }
}

//-------------------------------------------------