#sql("SelectOfficial")
  SELECT * from Stick WHERE nickname = ? AND vid = ? AND  identifying = '2'
#end

#sql("SelectCount")
  SELECT  count(*) as ct FROM  Stick WHERE  sitz = ? AND identifying = '1'
#end

#sql("SelectCheck")
  SELECT * FROM Stick WHERE vid = ? AND identifying = '1' limit 1
#end
