package com.estsoft.paldotourism.service;

import com.estsoft.paldotourism.entity.Bus;
import com.estsoft.paldotourism.entity.Likes;
import com.estsoft.paldotourism.entity.User;
import com.estsoft.paldotourism.exception.likes.AlreadyLikedException;
import com.estsoft.paldotourism.exception.likes.NotLikedYetException;
import com.estsoft.paldotourism.repository.BusRepository;
import com.estsoft.paldotourism.repository.LikesRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {

    private final LikesRepository likesRepository;
    private final BusRepository busRepository;

    public void addLike(User currentUser, Long busId) {
        Bus bus = validateBus(busId);

        Optional<Likes> like = likesRepository.findByUserIdAndBusId(currentUser.getId(),bus.getId());

        if(like.isPresent()) {
            throw new AlreadyLikedException();
        }

        Likes newLike = Likes.builder()
            .user(currentUser)
            .bus(bus)
            .build();
        likesRepository.save(newLike);
    }

    public void cancelLike(User currentUser, Long busId) {
        Bus bus = validateBus(busId);

        Optional<Likes> like = likesRepository.findByUserIdAndBusId(currentUser.getId(),bus.getId());

        if(like.isEmpty()) {
            throw new NotLikedYetException();
        }

        likesRepository.delete(like.get());
    }

    private Bus validateBus(Long busId) {
        return busRepository.findById(busId).orElseThrow(IllegalArgumentException::new);
    }
}
