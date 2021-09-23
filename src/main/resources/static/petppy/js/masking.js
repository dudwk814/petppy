let maskingFunc = {
    checkNull : function (str){
        if(typeof str == "undefined" || str == null || str == ""){
            return true;
        } else {
            return false;
        }
    },

    /* ※ 이메일 마스킹
    ex1) 원본 데이터 : abcdefg12345@naver.com
        변경 데이터 : ab**********@naver.com
    ex2) 원본 데이터 : abcdefg12345@naver.com
        변경 데이터 : ab**********@nav******
    */

    email : function(str){
        let originStr = str;
        let emailStr = originStr.match(/([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9._-]+)/gi);
        let strLength; if(this.checkNull(originStr) == true || this.checkNull(emailStr) == true){
            return originStr;
        }else{
            strLength = emailStr.toString().split('@')[0].length - 3;
            // ex1) abcdefg12345@naver.com => ab**********@naver.com
            // return originStr.toString().replace(new RegExp('.(?=.{0,' + strLength + '}@)', 'g'), '*');
            // ex2) abcdefg12345@naver.com => ab**********@nav******
            return originStr.toString().replace(new RegExp('.(?=.{0,' + strLength + '}@)', 'g'), '*').replace(/.{6}$/, "******");
        }
    },

    /* ※ 휴대폰 번호 마스킹
    ex1) 원본 데이터 : 01012345678, 변경 데이터 : 010****5678
    ex2) 원본 데이터 : 010-1234-5678, 변경 데이터 : 010-****-5678
    ex3) 원본 데이터 : 0111234567, 변경 데이터 : 011***4567
    ex4) 원본 데이터 : 011-123-4567, 변경 데이터 : 011-***-4567
    */
    phone : function(str){
        let originStr = str;
        let phoneStr;
        let maskingStr;

        if(this.checkNull(originStr) == true){
            return originStr;
        }

        if (originStr.toString().split('-').length != 3)
        { // 1) -가 없는 경우
            phoneStr = originStr.length < 11 ? originStr.match(/\d{10}/gi) : originStr.match(/\d{11}/gi);
            if(this.checkNull(phoneStr) == true){
                return originStr;
            }


            if(originStr.length < 11) { // 1.1) 0110000000
                 maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{3})(\d{4})/gi,'$1***$3'));
            } else { // 1.2) 01000000000
                 maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/(\d{3})(\d{4})(\d{4})/gi,'$1****$3'));
            }
        }else { // 2) -가 있는 경우
             phoneStr = originStr.match(/\d{2,3}-\d{3,4}-\d{4}/gi);
             if(this.checkNull(phoneStr) == true){
                 return originStr;
             }
             if(/-[0-9]{3}-/.test(phoneStr)) { // 2.1) 00-000-0000
                  maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{3}-/g, "-***-"));
             } else if(/-[0-9]{4}-/.test(phoneStr)) { // 2.2) 00-0000-0000
                  maskingStr = originStr.toString().replace(phoneStr, phoneStr.toString().replace(/-[0-9]{4}-/g, "-****-"));
             }
        } return maskingStr;
    },

    /*
    ※ 주민등록 번호 마스킹 (Resident Registration Number, RRN Masking)
    ex1) 원본 데이터 : 990101-1234567, 변경 데이터 : 990101-1******
    ex2) 원본 데이터 : 9901011234567, 변경 데이터 : 9901011******
    */
    rrn : function(str){
        let originStr = str;
        let rrnStr;
        let maskingStr;
        let strLength;

        if(this.checkNull(originStr) == true){
            return originStr;
        }

        rrnStr = originStr.match(/(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4]{1}[0-9]{6}\b/gi);
        if(this.checkNull(rrnStr) == false){
            strLength = rrnStr.toString().split('-').length;
            maskingStr = originStr.toString().replace(rrnStr,rrnStr.toString().replace(/(-?)([1-4]{1})([0-9]{6})\b/gi,"$1$2******"));
        }else {
            rrnStr = originStr.match(/\d{13}/gi);
            if(this.checkNull(rrnStr) == false){
                strLength = rrnStr.toString().split('-').length;
                maskingStr = originStr.toString().replace(rrnStr,rrnStr.toString().replace(/([0-9]{6})$/gi,"******"));
            }else{
                return originStr;
            }
        } return maskingStr;
    },

    /*
    ※ 이름 마스킹
    ex1) 원본 데이터 : 갓댐희, 변경 데이터 : 갓댐*
    ex2) 원본 데이터 : 하늘에수, 변경 데이터 : 하늘**
    ex3) 원본 데이터 : 갓댐, 변경 데이터 : 갓*
    */

    name : function(str){
        let originStr = str;
        let maskingStr;
        let strLength;

        if(this.checkNull(originStr) == true){
            return originStr;
        }
        strLength = originStr.length;

        if(strLength < 3){
            maskingStr = originStr.replace(/(?<=.{1})./gi, "*");
        }else {
            maskingStr = originStr.replace(/(?<=.{2})./gi, "*");
        }
        return maskingStr;
    }
}