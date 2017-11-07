#sql("PictureUrl")
  SELECT * from view WHERE v_id = ?
#end

#sql("DeleteAdd")
  delete from view where v_id = ?
#end