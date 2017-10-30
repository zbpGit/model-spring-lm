#sql("SelectAll")
  select * from annunciate as a,annunciatetype as b
   where a.work_type = b.antype AND audit = 1 AND remove = '0'
   #if(type??)
    and work_type = #para(type)
  #end
  #if(sort == "deadline")
  order by deadline DESC
  #end
  #if(sort == "number")
  order by number DESC
  #end
#end

#sql("UpdateHit")
  UPDATE annunciate set hit= hit +1 WHERE vid = ?
#end

#sql("SelectAM")
  select a.*,b.name,b.hurl from annunciate as a,model as b where a.uid = b.nickname and a.vid = ?
#end

#sql("SelectPage")
  select * from annunciate where official = #para(official) and top = #para(top)
  #if(address??)
    and site like #para(address)
  #end
  #if(type??)
    and work_type = #para(type)
  #end
  #if(sort == "deadline")
  order by deadline DESC
  #end
  #if(sort == "number")
  order by number DESC
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
