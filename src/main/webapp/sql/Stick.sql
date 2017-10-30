#sql("SelectOfficial")
  SELECT * from stick WHERE nickname = ? AND vid = ? AND  identifying = '2'
#end

#sql("SelectCount")
  SELECT  count(*) as ct FROM  stick WHERE  sitz = ? AND identifying = '1'
#end

#sql("SelectCheck")
  SELECT * FROM stick WHERE vid = ? AND identifying = '1' limit 1
#end
