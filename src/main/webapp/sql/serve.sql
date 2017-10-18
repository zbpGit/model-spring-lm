#sql("serve")
  select * from serve where type = ?
#end

#sql("SelectTime")
  SELECT * FROM serve WHERE time = '官方' and type = ?
#end

#sql("moneyType")
  SELECT * FROM serve WHERE money = ? and type = ?
#end