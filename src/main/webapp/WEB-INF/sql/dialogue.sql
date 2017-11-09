#sql("userAll")
    select * from dialogue where cid = ? and user =? and View = '未查看'
#end

#sql("dialogue")
  select a.hurl,a.name,b.* from model as a,dialogue as b where a.nickname = b.user and b.cid = ? ORDER BY b.SendTime
#end

#sql("unread")
  select a.hurl,a.name,(select count(*) from dialogue where user = ? and View = '未查看') as abc from model as a
  where a.nickname = ?
#end