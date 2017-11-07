#sql("serve")
  select se_id,se_name,money,time from serve where type = ? LIMIT 3
#end

#sql("SelectTime")
  SELECT * FROM serve WHERE time = '官方' and type = ?
#end

#sql("moneyType")
  SELECT * FROM serve WHERE money = ? and type = ?
#end