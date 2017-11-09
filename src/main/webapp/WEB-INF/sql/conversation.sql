#sql("exist")
  select * from conversation where sender = ? and recipient =?
#end

#sql("senderAll")
  select * from conversation where sender = ?
#end

#sql("recipientAll")
  select * from conversation where recipient = ?
#end