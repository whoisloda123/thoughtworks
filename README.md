# thoughtworks
解决第二个Conference Track Management问题  
1.流程  
   1. 从资源文件路径读取input.txt文件，里面每一行即为一个talk  
   2. 检查每个talk内容是否有效，读取时间、title等放入链表talk类   
   3. 对list通过talk.duration进行降序排序
   4. find the possible days to schedule conference.  
   5. 从集合里面找到满足duration加起来等于180的组合 (morning session总共是180分钟)  
   6. 从剩下的里面找到满足duration加起来大于等于180小于240的组合 (the evening sessions的时间需要 180 >= the evening sessions <= 240)  
   7. 如果集合里面还有剩余，则分配失败  
2.如何使用  
   1. 调用ConferenceManager.tracks传入文件名即可，或者直接调用TrackFactory.newTracks传入构造好的List<Talk>  
3.如何测试    
   1.修改resources/input.txt下文件内容，运行ConferenceManagerTest.tracks单元测试接口即可