/**
 * 
 */
 
 const selectBtn = document.querySelector(".select-btn"),
      items = document.querySelectorAll(".item");
 
 selectBtn.addEventListener("click", () => {
    selectBtn.classList.toggle("open");
});

items.forEach(item => {
    item.addEventListener("click", () => {
        item.classList.toggle("checked");

        let checked = document.querySelectorAll(".checked"),
            btnText = document.querySelector(".btn-text"),
            itemText = document.querySelector(".item-text.value");

            if(checked && checked.length > 0){
                btnText.innerText = `${checked.length} Selected`;
                console.log(btnText.innerText);
            }else{
                btnText.innerText = "근무 가능 지역";
            }
    });
})