#sql("SelectOfficial")
  SELECT * from Stick WHERE nickname = ? AND vid = ? AND  identifying = '2'
#end

#sql("SelectCount")
  SELECT  count(*) as ct FROM  Stick WHERE  sitz = ? AND identifying = '1'
#end

#sql("SelectCheck")
  SELECT * FROM Stick WHERE vid = ? AND identifying = '1' and payment = '1' limit 1
#end

#sql("UpdateRemove")
  update Stick set stopTime = ?,identifying = ? where vid = ? and  payment = '1'
#end

#sql("SelectExist")
  select * from Stick where out_trade_no = ?
#end

#sql("SelectLater")
  SELECT  count(id) as quantity FROM  Stick WHERE  sitz = ? AND identifying = '1' AND stopTime = '0' and payment = '1'
#end

#sql("SelectAll")
  SELECT * FROM Stick WHERE identifying = '1' and payment = '1' Order By nowTime
#end

