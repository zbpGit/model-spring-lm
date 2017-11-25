#sql("SelectCount")
  SELECT count(*) as ct  from enjoy WHERE type = ? and oneself = ?  and  look = '0'
#end

#sql("SelectEM")
  select a.*,b.name,b.hurl from enjoy as a,model as b where a.nickname = b.nickname and a.type = ? and a.identifying = 0 and  a.oneself =?
#end

#sql("SelectGuestbook")
  select a.*,b.name,b.hurl  from enjoy as a,model as b where a.nickname = b.nickname and a.type = ? and  a.id = ?
#end

#sql("SelectLike")
  select * from enjoy WHERE type =1 AND wid = ? AND nickname = ? LIMIT 1
#end


