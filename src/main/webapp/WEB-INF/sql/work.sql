#sql("SelectStickC")
  SELECT count(*) as ct FROM work where Stick = 1
#end

#sql("SelectStick")
  SELECT id from work WHERE Stick = 1 AND  exist= 1
#end

#sql("SelectWP")
select b.picture,a.id,a.age,a.name,a.sex,a.city,a.region,a.attention,a.examine,a.exist,a.workJob,a.stature,a.weight,a.surround from work as a,workpicture as b
where a.id = b.wid and a.id = ? and b.type = 1 and a.exist = 1 order by b.id LIMIT 1
#end

#sql("SelectMo")
SELECT id FROM work WHERE exist = 1 AND Stick = 0
#if(region??)
  and region like #para(region)
#end
#if(workJob??)
and workJob like #para(workJob)
#end
#if(workType??)
and workType like #para(workType)
#end
#if(rank == "attention")
order by attention DESC
#end
#if(rank == "examine")
order by examine DESC
#end
#if(rank == "times")
order by times DESC
#end
#end

#sql("workNickName")
  SELECT  id FROM work WHERE wid = ? and exist BETWEEN  0 and 1
#end

#sql("Judge")
select b.picture,a.id,a.age,a.name,a.region,a.attention,a.examine,a.exist from work as a,workpicture as b where a.id = b.wid and a.wid = ? and a.exist BETWEEN  0 and 1  and b.type = 1 order by b.id LIMIT 1
#end

#sql("workAttention")
  update work set attention = attention+1 where id = ?
#end

#sql("UpdateHit")
  UPDATE work set examine= examine + ? WHERE id = ?
#end
