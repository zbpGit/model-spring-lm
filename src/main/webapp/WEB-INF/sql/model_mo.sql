#sql("DMM")
  delete FROM  model_mo where mid = ? and moid = ?
#end

#sql("Selectmid")
  SELECT * FROM model_mo WHERE mid = ?
#end

#sql("SelectMo")
  select * from model_mo where mid = ? and moid = ?
#end

#sql("DeleteMoid")
  delete from model_mo where mid = ? and moid = ?
#end