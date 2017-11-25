#sql("SelectAll")
  select * from annunciate as a,annunciatetype as b
   where a.work_type = b.antype AND a.audit = 1 AND a.remove = '0'
   #if(type??)
    and a.work_type = #para(type)
  #end
  #if(sort == "deadline")
  order by a.deadline DESC
  #end
  #if(sort == "number")
  order by a.number DESC
  #end
#end

#sql("UpdateHit")
  UPDATE annunciate set hit= hit + ? WHERE vid = ?
#end

#sql("SelectAM")
  select a.*,b.name,b.hurl from annunciate as a,model as b where a.uid = b.nickname and a.vid = ?
#end

#sql("SelectPage")
  select * from annunciate as a,annunciatetype as b
   where a.work_type = b.antype and a.official = #para(official) and a.top = #para(top)
  #if(address??)
    and a.site like #para(address)
  #end
  #if(type??)
    and a.work_type = #para(type)
  #end
  #if(sort == "deadline")
  order by a.deadline DESC
  #end
  #if(sort == "number")
  order by a.number DESC
  #end
#end

#sql("list")
  select * from annunciate where 1 = 1
  #if(work_type??)
    and work_type = #para(work_type)
  #end
#end

#sql("SelectAnnunciate")
  select * from annunciate where vid = ?
#end

#sql("SelectStick")
  SELECT site FROM annunciate WHERE vid = ?
#end

#sql("Annunciateexamine")
  select * from annunciate as a,annunciatetype as b where a.work_type = b.antype and a.uid =?  and remove = '0' order by a.top DESC,a.deadline DESC
#end

#sql("UpdateEnough")
  UPDATE annunciate set enough = ? where vid = ?
#end

#sql("UpdateRemove")
  UPDATE annunciate  SET  remove = '1',top = '0' WHERE vid = ?
#end

#sql("QuantityArea")
  select count(*) as quantity from annunciate where audit = 1 AND remove = '0' and site = ?
#end

#sql("SelectLately")
  select count(*) as quantity from annunciate where audit = 1 AND remove = '0' and top = "1" and site = ?
#end

