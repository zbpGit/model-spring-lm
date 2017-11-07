#sql("Identifying")
  SELECT * from wStick where identifying = '1' and vid = ? limit 1
#end