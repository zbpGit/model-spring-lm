$(document).ready(function () {
    $("button").click(function(){
        $.post("/Annunciate/Collect",
            {
                vid:966979206,
                uid:"o2u1CxMrsU91pJxaxlTOC_TBDKmY"
            },
            function(data,status){
                console.log(data.result+status)
            });
    });
})
