package com.example.newsfeed.friend.service;

import com.example.newsfeed.common.dto.ResponseDto;
import com.example.newsfeed.friend.dto.FriendRequestDto;
import com.example.newsfeed.friend.dto.FriendResponseDto;
import com.example.newsfeed.friend.entity.Friend;
import com.example.newsfeed.friend.entity.FriendShip;
import com.example.newsfeed.friend.repository.FriendRepository;
import com.example.newsfeed.friend.repository.FriendShipRepository;
import com.example.newsfeed.users.entity.Users;
import com.example.newsfeed.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendShipService {
    private final FriendRepository friendRepository;
    private final FriendShipRepository friendShipRepository;
    private final UsersRepository usersRepository;

    @Transactional //친구 요청
    public ResponseDto<FriendResponseDto> request(FriendRequestDto dto, String userId){
        // 요청 보낸 사용자 찾기
        Users fromUser = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Users toUser = usersRepository.findById(dto.getFriend())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        // 친구 요청을 받는 사용자 찾기
//        Users toUser = dto.getFriend();  //FriendRequestDto에서 친구 객체를 받아서 바로 사용 id
        //friendid로 받아와서 유저를 확인하고 받아와야함.


//        if (friendRepository.existsByUsersAndFriend(fromUser, toUser)) {
//            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
//        }

        // 기존 친구 요청이 있는지 확인 (중복 요청 방지)
        if(friendRepository.findByFriendIdAndUsersId(dto.getFriend(), userId).isPresent()){
            throw new IllegalStateException("이미 친구 요청을 보냈습니다.");
        }

        // 새로운 친구 요청 생성 (상태: 0 = 요청 상태)
        Friend friendRequest = new Friend(fromUser, toUser);
        friendRepository.save(friendRequest);

        // 응답 DTO
        return ResponseDto.success(new FriendResponseDto(friendRequest.getId(), fromUser.getId(), toUser.getId(), friendRequest.getCreatedAt()));
    }

    @Transactional // 친구 요청 수락
    public String acceptFriend(Long friendId, String userId) {
        // 요청 받은 사람
        Users toUser = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Friend friendRequest = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("friend_id가 없습니다. _" + friendId));

        // 요청 보낸 사람
        Users fromUser =  usersRepository.findById(friendRequest.getUsers().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        FriendShip acceptFriend = new FriendShip(toUser, fromUser, (short) 1);
        friendShipRepository.save(acceptFriend);
        
//        Users toUser = usersRepository.findById(friendRequest.getFriend().getId())
//                .orElseThrow(() -> new IllegalArgumentException("요청 받은 사용자가 존재하지 않습니다."));

        // 친구 요청 수락: FriendShip 테이블에 저장
//        FriendShip toUserFriendship = new FriendShip(toUser, fromUser, (short) 1);
        //FriendShip fromUserFriendship = new FriendShip(fromUser, toUser, (short) 1);

        return "친구 추가 완료";
    }

    @Transactional //친구 요청 거절
    public String rejectFriend(Long friendId, String userId){//컨트롤러
        // 요청 받은사람
        Users toUser = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        Friend friendRequest = friendRepository.findById(friendId)
                .orElseThrow(() -> new IllegalArgumentException("friend_id가 없습니다. _" + friendId));

        // 요청 보낸 사람
        Users fromUser =  usersRepository.findById(friendRequest.getUsers().getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        FriendShip acceptFriend = new FriendShip(toUser, fromUser, (short) 2);
        friendShipRepository.save(acceptFriend);

//        Users toUser = usersRepository.findById(dto.getFriend().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Friend not found with id: " + dto.getFriend().getId()));

        // 친구 요청을 거절하는 새로운 FriendShip 객체 생성 (status: 2 = 거절 상태)
        return "친구 거절 완료";//friendShip.getStatus()
    }

    public List<FriendResponseDto> findAll(String userId){//다건 조회 //조회조건 id로
        return friendShipRepository.findAllByUserIdAndStatus(userId, (short)1)
                .stream().map(FriendResponseDto::toDto).toList();

    }

    public FriendResponseDto findOne(Long id){//단건 조회
        Friend findFriend = friendRepository.findByIdOrElseThrow(id); // 1. id 대조해서 그 id에 맞는 데이터를 조회함.
        return new FriendResponseDto(findFriend.getId(), findFriend.getUsers().getId(), findFriend.getFriend().getId(), findFriend.getCreatedAt());
    }

//6가지
//성공 실패 여부
}
