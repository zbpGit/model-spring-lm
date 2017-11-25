#sql("Identifying")
  SELECT * from wStick where identifying = '1' and payment = '1' and vid = ? limit 1
#end

#sql("EndDusinessCard")
  update wStick set stopTime = ?,identifying= ? where vid = ? and payment = '1'
#end

#sql("SelectOut")
  select * from wStick WHERE out_trade_no = ?
#end

#sql("UpdateOut")
  update wStick set identifying= ?, payment = ? where out_trade_no = ?
#end

#sql("SelectAll")
  SELECT * FROM wStick where identifying = "1" and payment = "1"  Order By nowTime
#end

#sql("SelectNext")
  SELECT * FROM wStick where identifying = "1" and payment = "1"  and stopTime = "0" and vid = ? Order By nowTime
#end

#sql("SelectFollowUp")
  SELECT * FROM wStick where identifying = "1" and payment = "1"  and stopTime = "0" Order By nowTime
#end