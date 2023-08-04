package uz.anime.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anime.domains.Lists;
import uz.anime.dtos.lists.ListsCreateDTO;
import uz.anime.dtos.lists.ListsUpdateDTO;
import uz.anime.repositories.ListsRepository;
import uz.anime.utils.MediaUtils;

import java.util.List;

import static uz.anime.mapper.ListsMapper.LISTS_MAPPER;

@Service
@RequiredArgsConstructor
public class ListsService {
    private final ListsRepository listsRepository;

    public Lists create(ListsCreateDTO dto) {
        Lists lists = LISTS_MAPPER.toEntity(dto);
        return listsRepository.save(lists);
    }

    public Lists findById(Integer id) {
        return listsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("List not found with id '%s'".formatted(id)));
    }

    public List<Lists> findByTypeId(Integer typeId) {
        return listsRepository.findByTypeId(typeId);
    }

    public Lists update(ListsUpdateDTO dto) {
        Lists lists = findById(dto.getId());
        LISTS_MAPPER.updateListsFromDTO(dto, lists);
        return update(lists);
    }

    public Lists update(Lists lists) {
        return listsRepository.save(lists);
    }

    public void delete(Integer id) {
        Lists lists = findById(id);
        listsRepository.delete(lists);
        update(lists);
    }
}
