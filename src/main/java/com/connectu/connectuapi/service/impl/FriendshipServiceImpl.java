package com.connectu.connectuapi.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.connectu.connectuapi.dao.FriendshipDao;
import com.connectu.connectuapi.dao.UserDao;
import com.connectu.connectuapi.domain.Category;
import com.connectu.connectuapi.domain.Friendship;
import com.connectu.connectuapi.domain.Thread;
import com.connectu.connectuapi.domain.User;
import com.connectu.connectuapi.service.IFriendshipService;
import com.connectu.connectuapi.service.IUserService;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class FriendshipServiceImpl extends MPJBaseServiceImpl<FriendshipDao, Friendship> implements IFriendshipService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private FriendshipDao friendshipDao;

    public List<User> following(Integer followingId){
        MPJLambdaWrapper<User> userWrapper = new MPJLambdaWrapper<>();
        userWrapper.innerJoin(Friendship.class, Friendship::getFollowerId, User::getUserId)
                .eq(Friendship::getFollowingId, followingId);
        return userDao.selectList(userWrapper);
    }

    public String followingNum(Integer followingId){
        LambdaQueryWrapper<Friendship> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Friendship::getFollowingId, followingId);
        User user=userDao.selectById(followingId);
        user.setFollowingCount(friendshipDao.selectCount(lqw).intValue());
        userDao.updateById(user);
        return friendshipDao.selectCount(lqw).toString();
    }


    public List<User> follower(Integer followerId){
        MPJLambdaWrapper<User> userWrapper = new MPJLambdaWrapper<>();
        userWrapper.innerJoin(Friendship.class, Friendship::getFollowingId, User::getUserId)
                .eq(Friendship::getFollowerId, followerId);
        return userDao.selectList(userWrapper);
    }
    public List<User> followerThread(Integer followerId){
        MPJLambdaWrapper<User> userWrapper = new MPJLambdaWrapper<>();
        userWrapper
                .selectAll(Thread.class)
                .selectAll(User.class)
                .selectAll(Category.class)
                .selectAll(User.class)
                .innerJoin(Friendship.class, Friendship::getFollowingId, User::getUserId)
                .innerJoin(Thread.class, Thread::getUserId, User::getUserId)
                .eq(Friendship::getFollowerId, followerId);
        return userDao.selectList(userWrapper);
    }

    public String followerNum(Integer followerId){
        LambdaQueryWrapper<Friendship> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Friendship::getFollowerId, followerId);
        User user=userDao.selectById(followerId);
        user.setFollowedByCount(friendshipDao.selectCount(lqw).intValue());
        userDao.updateById(user);
        return friendshipDao.selectCount(lqw).toString();
    }


    public boolean saveOrRemove(Integer followerId, Integer followingId) {
        LambdaQueryWrapper<Friendship> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Friendship::getFollowerId, followerId)
                .eq(Friendship::getFollowingId, followingId);
        List<Friendship> friendships = friendshipDao.selectList(lqw);
        if(friendships.size()!=0){
            System.out.println("not null..." + friendships.size());
            return super.removeByIds(friendshipDao.selectList(lqw));
        }
        Friendship friendship = new Friendship();
        friendship.setFollowerId(followerId);
        friendship.setFollowingId(followingId);
        return super.save(friendship);
    }


}
