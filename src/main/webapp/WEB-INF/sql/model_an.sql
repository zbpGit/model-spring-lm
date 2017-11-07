#sql("pdsc")
  select * from model_an where mid = ? and aid = ?
#end

#sql("SelectAnnunciate")
  SELECT aid as id from model_an where mid = ?
#end

#sql("MDA")
  delete FROM  model_an where mid = ? and aid = ?
#end