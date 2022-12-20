/**
 * 
 */
 
 var employs={
    permanent:['정규직', '기간의 정함이 없는 근로 계약/파견근로 비희망/ 대체인력채용 비희망'
,'기간의 정함이 없는 근로계약(시간(선택)제)/ 파견근로 비희망']
    ,contract:['계약직', '기간의 정함이 있는 근로계약/ 파견근로 비희망/ 대체인력채용 비희망',
'기간의 정함이 있는 근로계약(시간(선택)제)/ 파견근로 비희망/ 대체인력채용 비희망',
'기간의 정함이 있는 근로계약/ 계약기간 만료 후 상용직전환검토/ 파견근로 비희망/ 대체인력 채용 비희망' ]
    ,intern:['인턴직']
    ,partTime:['기타/아르바이트', '프리랜서']
}

var main= document.getElementById('main_menu');
var sub = document.getElementById('sub_menu');

main.addEventListener('change',function(){

    var selected_option = employs[this.value];

    while(sub.options.length>0){
        sub.options.remove(0);
    }

    Array.from(selected_option).forEach(function(el){

        let option = new Option(el, el);

        sub.appendChild(option);
    })
});